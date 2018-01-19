package services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import models.JsonSupport
import models.db.CMCDataEntityTable
import spray.json._
import utils.{Cache, CacheKey}

import scala.concurrent.{ExecutionContext, Future}

class TradeService(val databaseService: DatabaseService)(
  implicit executionContext: ExecutionContext,
  actorSystem: ActorSystem,
  materializer: ActorMaterializer
) extends JsonSupport with CMCDataEntityTable {

  import databaseService._
  import databaseService.driver.api._

  val BTC_STR = "BTC"
  val BCH_STR = "BCH"
  val BCC_STR = "BCC"
  val BNB_STR = "BNB"
  val USD_STR = "USD"
  val NG_STR = Seq(
    BCH_STR, BCC_STR, BNB_STR, USD_STR
  )

  case class BinanceCurrenciesResponse(
                                        name: String,
                                        price: String,
                                        yen: String,
                                        rank: Option[String],
                                        availableSupply: Option[String],
                                        totalSupply: Option[String],
                                        maxSupply: Option[String],
                                        canTrex: Boolean,
                                        canPolo: Boolean
                                      )

  case class HitBTCCurrenciesResponse(name: String, price: Option[String], yen: Option[String], canBinance: Boolean, canTrex: Boolean, canPolo: Boolean)

  case class CryptopiaCurrenciesResponse(name: String, price: Option[String], yen: Option[String], canBinance: Boolean, canTrex: Boolean, canPolo: Boolean)

  case class StocksCurrenciesResponse(name: String, buy: String, buyYen: String, sell: String, sellYen: String, canBinance: Boolean, canTrex: Boolean, canPolo: Boolean)

  def saveCmCData: Future[String] = {
    requestCMCAPI.flatMap { data =>
      val tweetData = DBIO.sequence(
        data.map { d =>
          cmcData.insertOrUpdate(
            CmcDataRow(
              id = d.id,
              name = d.name,
              symbol = d.symbol,
              rank = d.rank,
              priceBtc = d.price_btc,
              availableSupply = d.available_supply,
              totalSupply = d.total_supply,
              maxSupply = d.max_supply,
              percentChange1h = d.percent_change_1h,
              percentChange7d = d.percent_change_7d,
              percentChange24h = d.percent_change_24h,
              lastUpdated = d.last_updated
            )
          )
        }
      )
      db.run(tweetData).flatMap { _ =>
        Future successful "ok"
      }
    }
  }

  def getBinanceCurrencies: Future[Seq[BinanceCurrenciesResponse]] = {
    def getCurrencies() = {
      val currencies = for {
        binance <- requestBinanceAPI
        bittrex <- requestBittrexAPI
        poloniex <- requestPoloniexAPI
        bitFlyer <- requestBitFlyerAPI
      } yield {
        (binance, bittrex, poloniex, bitFlyer)
      }
      currencies.flatMap {
        case (binanceCurrencies, bittrexCurrencies, poloniexCurrencies, bitFlyerPrice) =>
          convertFutureSeq(
            binanceCurrencies.map(c =>
              db.run(cmcData.filter(_.symbol === c.symbol).result).flatMap { result =>
                val (rank, availableSupply, totalSupply, maxSupply) = result.headOption match {
                  case Some(r) => (Some(r.rank), r.availableSupply, r.totalSupply, r.maxSupply)
                  case None => (None, None, None, None)
                }
                Future successful
                  BinanceCurrenciesResponse(
                    c.symbol,
                    c.price,
                    BigDecimal(bitFlyerPrice.ltp * c.price.toDouble)
                      .setScale(4, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
                    rank = rank,
                    availableSupply = availableSupply,
                    totalSupply = totalSupply,
                    maxSupply = maxSupply,
                    canTrex = bittrexCurrencies.result.exists(_.Currency == c.symbol),
                    canPolo = poloniexCurrencies.get(c.symbol).isDefined
                  )
              }
            )
          )
      }
    }

    cacheAction[Seq[BinanceCurrenciesResponse]](CacheKey.binance, getCurrencies)
  }

  def getHitBTCCurrencies: Future[Seq[HitBTCCurrenciesResponse]] = {
    def getCurrencies() = {
      val currencies = for {
        hitbtc <- requestHitBTCAPI
        binance <- requestBinanceAPI
        bittrex <- requestBittrexAPI
        poloniex <- requestPoloniexAPI
        bitFlyer <- requestBitFlyerAPI
      } yield {
        (hitbtc, binance, bittrex, poloniex, bitFlyer)
      }
      currencies.flatMap {
        case (hitbtc, binanceCurrencies, bittrexCurrencies, poloniexCurrencies, bitFlyerPrice) =>
          Future successful hitbtc.map(c =>
            HitBTCCurrenciesResponse(
              c.symbol,
              c.last,
              c.last.map(price => BigDecimal(bitFlyerPrice.ltp * price.toDouble)
                .setScale(4, scala.math.BigDecimal.RoundingMode.HALF_UP).toString),
              canBinance = binanceCurrencies.exists(_.symbol == c.symbol),
              canTrex = bittrexCurrencies.result.exists(_.Currency == c.symbol),
              canPolo = poloniexCurrencies.get(c.symbol).isDefined
            )
          )
      }
    }

    cacheAction[Seq[HitBTCCurrenciesResponse]](CacheKey.hitbtc, getCurrencies)
  }

  def getCryptopiaCurrencies: Future[Seq[CryptopiaCurrenciesResponse]] = {
    def getCurrencies() = {
      val currencies = for {
        cryptopia <- requestCryptopiaAPI
        binance <- requestBinanceAPI
        bittrex <- requestBittrexAPI
        poloniex <- requestPoloniexAPI
        bitFlyer <- requestBitFlyerAPI
      } yield {
        (cryptopia, binance, bittrex, poloniex, bitFlyer)
      }
      currencies.flatMap {
        case (cryptopia, binanceCurrencies, bittrexCurrencies, poloniexCurrencies, bitFlyerPrice) =>
          Future successful cryptopia.map(c =>
            CryptopiaCurrenciesResponse(
              c.Label,
              c.LastPrice.map(_.toString),
              c.LastPrice.map(price => BigDecimal(bitFlyerPrice.ltp * price)
                .setScale(4, scala.math.BigDecimal.RoundingMode.HALF_UP).toString
              ),
              canBinance = binanceCurrencies.exists(_.symbol == c.Label),
              canTrex = bittrexCurrencies.result.exists(_.Currency == c.Label),
              canPolo = poloniexCurrencies.get(c.Label).isDefined
            )
          )
      }
    }

    cacheAction[Seq[CryptopiaCurrenciesResponse]](CacheKey.cryptopia, getCurrencies)
  }

  def getStocksCurrencies: Future[Seq[StocksCurrenciesResponse]] = {
    def getCurrencies() = {
      val currencies = for {
        stocks <- requestStocksAPI
        binance <- requestBinanceAPI
        bittrex <- requestBittrexAPI
        poloniex <- requestPoloniexAPI
        bitFlyer <- requestBitFlyerAPI
      } yield {
        (stocks, binance, bittrex, poloniex, bitFlyer)
      }
      currencies.flatMap {
        case (stocks, binanceCurrencies, bittrexCurrencies, poloniexCurrencies, bitFlyerPrice) =>
          Future successful stocks.map { c =>
            val buy = c.buy.toString().replaceAll("\"", "")
            val sell = c.sell.toString().replaceAll("\"", "")
            StocksCurrenciesResponse(
              c.market_name,
              buy,
              BigDecimal(bitFlyerPrice.ltp * buy.toDouble)
                .setScale(4, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
              sell,
              BigDecimal(bitFlyerPrice.ltp * sell.toDouble)
                .setScale(4, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
              canBinance = binanceCurrencies.exists(_.symbol == c.market_name),
              canTrex = bittrexCurrencies.result.exists(_.Currency == c.market_name),
              canPolo = poloniexCurrencies.get(c.market_name).isDefined
            )
          }
      }
    }

    cacheAction[Seq[StocksCurrenciesResponse]](CacheKey.stocks, getCurrencies)
  }

  def btcToJpy(btc: Double): Future[BtcToJpy] = {
    val prices = for {
      bitFlyer <- requestBitFlyerAPI
      coincheck <- requestCoincheckAPI
      zaif <- requestZaifAPI
    } yield {
      (bitFlyer, coincheck, zaif)
    }
    prices.flatMap {
      case (bitFlyer, coincheck, zaif) =>
        Future successful BtcToJpy(
          BigDecimal(bitFlyer.ltp * btc)
            .setScale(8, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
          BigDecimal(coincheck.last * btc)
            .setScale(8, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
          BigDecimal(zaif.last_price * btc)
            .setScale(8, scala.math.BigDecimal.RoundingMode.HALF_UP).toString
        )
    }
  }

  def jpyToBtc(jpy: Double): Future[JpyToBtc] = {
    val prices = for {
      bitFlyer <- requestBitFlyerAPI
      coincheck <- requestCoincheckAPI
      zaif <- requestZaifAPI
    } yield {
      (bitFlyer, coincheck, zaif)
    }
    prices.flatMap {
      case (bitFlyer, coincheck, zaif) =>
        Future successful JpyToBtc(
          BigDecimal(jpy / bitFlyer.ltp)
            .setScale(8, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
          BigDecimal(jpy / coincheck.last)
            .setScale(8, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
          BigDecimal(jpy / zaif.last_price)
            .setScale(8, scala.math.BigDecimal.RoundingMode.HALF_UP).toString
        )
    }
  }

  private def requestCMCAPI = {
    val queryString = Math.random()
    request("api.coinmarketcap.com", HttpRequest(uri = s"https://api.coinmarketcap.com/v1/ticker/?$queryString&limit=0")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestCMCAPI", res)
        Future successful res.parseJson.convertTo[Seq[CMCData]]
      }
    }
  }

  private def requestBinanceAPI = {
    request("api.binance.com", HttpRequest(uri = "https://api.binance.com/api/v1/ticker/allPrices")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestBinanceAPI", res)
        Future successful res.parseJson.convertTo[Seq[BinanceCurrency]]
          .filter(c =>
            c.symbol.contains(BTC_STR) && !NG_STR.exists(c.symbol.contains(_))
          )
          .map(c => c.copy(symbol = c.symbol.replace(BTC_STR, "")))
      }
    }
  }

  private def requestBittrexAPI = {
    request("bittrex.com", HttpRequest(uri = "https://bittrex.com/api/v1.1/public/getcurrencies")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestBittrexAPI", res)
        Future successful res.parseJson.convertTo[BittrexCurrencies]
      }
    }
  }

  private def requestPoloniexAPI = {
    request("poloniex.com", HttpRequest(uri = "https://poloniex.com/public?command=returnCurrencies")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestPoloniexAPI", res)
        Future successful res.parseJson.asJsObject.fields
      }
    }
  }

  private def requestHitBTCAPI = {
    request("api.hitbtc.com", HttpRequest(uri = "https://api.hitbtc.com/api/2/public/ticker")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestHitBTCAPI", res)
        Future successful res.parseJson.convertTo[Seq[HitBTCCurrency]]
          .filter(c =>
            c.symbol.contains(BTC_STR) && !NG_STR.exists(c.symbol.contains(_))
          )
          .map(c => c.copy(symbol = c.symbol.replace(BTC_STR, "")))
      }
    }
  }

  private def requestCryptopiaAPI = {
    request("www.cryptopia.co.nz", HttpRequest(uri = "https://www.cryptopia.co.nz/api/GetMarkets")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestCryptopiaAPI", res)
        Future successful res.parseJson.convertTo[CryptopiaCurrencies].Data
          .filter(c =>
            c.Label.contains(s"/$BTC_STR") && !NG_STR.exists(c.Label.contains(_))
          )
          .map(c => c.copy(Label = c.Label.replace(s"/$BTC_STR", "")))
      }
    }
  }

  private def requestStocksAPI = {
    request("stocks.exchange", HttpRequest(uri = "https://stocks.exchange/api2/prices")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestStocksAPI", res)
        Future successful res.parseJson.convertTo[Seq[StocksCurrency]]
          .filter(c =>
            c.market_name.contains(s"_$BTC_STR") && !NG_STR.exists(c.market_name.contains(_))
          )
          .map(c => c.copy(market_name = c.market_name.replace(s"_$BTC_STR", "")))
      }
    }
  }

  private def requestBitFlyerAPI = {
    request("api.bitflyer.jp", HttpRequest(uri = "https://api.bitflyer.jp/v1/getticker")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestBitFlyerAPI", res)
        Future successful res.parseJson.convertTo[BitFlyerPrice]
      }
    }
  }

  private def requestCoincheckAPI = {
    request("coincheck.com", HttpRequest(uri = "https://coincheck.com/api/ticker")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestCoincheckAPI", res)
        Future successful res.parseJson.convertTo[CoincheckPrice]
      }
    }
  }

  private def requestZaifAPI = {
    request("api.zaif.jp", HttpRequest(uri = "https://api.zaif.jp/api/1/last_price/btc_jpy")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestZaifAPI", res)
        Future successful res.parseJson.convertTo[ZaifPrice]
      }
    }
  }

  private def request(host: String, httpRequest: HttpRequest): Future[HttpResponse] = {
    val http = Http(actorSystem)
    val responseFuture = Source.single(httpRequest)
      .via(http.outgoingConnectionHttps(host = host))
      .runWith(Sink.head)
    responseFuture
  }

  private def cacheAction[T](key: String, getCurrencies: () => Future[T]): Future[T] = {
    Cache.get[T](key) match {
      case Some(value) => Future successful value
      case _ => getCurrencies().flatMap { res =>
        Cache.put(key, res)
        Future successful res
      }
    }
  }

  def convertFutureSeq[A](f: Seq[Future[A]]): Future[Seq[A]] = {
    Future.sequence(f.map(_.map(Some(_)).recover { case _ => None })).map(_.flatten)
  }
}