package services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import models.JsonSupport
import spray.json._

import scala.concurrent.{ExecutionContext, Future}

class TradeService()(
  implicit executionContext: ExecutionContext,
  actorSystem: ActorSystem,
  materializer: ActorMaterializer
) extends JsonSupport {

  val BTC_STR = "BTC"
  val BCH_STR = "BCH"
  val BCC_STR = "BCC"
  val BNB_STR = "BNB"
  val USD_STR = "USD"
  val NG_STR = Seq(
    BCH_STR, BCC_STR, BNB_STR, USD_STR
  )

  case class BinanceCurrenciesResponse(name: String, price: String, yen: String, canTrex: Boolean, canPolo: Boolean)

  case class HitBTCCurrenciesResponse(name: String, price: String, yen: String, canBinance: Boolean, canTrex: Boolean, canPolo: Boolean)

  case class CryptopiaCurrenciesResponse(name: String, price: String, yen: String, canBinance: Boolean, canTrex: Boolean, canPolo: Boolean)

  case class StocksCurrenciesResponse(name: String, buy: String, buyYen: String, sell: String, sellYen: String, canBinance: Boolean, canTrex: Boolean, canPolo: Boolean)

  def getBinanceCurrencies: Future[Seq[BinanceCurrenciesResponse]] = {
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
        Future successful binanceCurrencies.map(c =>
          BinanceCurrenciesResponse(
            c.symbol,
            c.price,
            BigDecimal(bitFlyerPrice.mid * c.price.toDouble)
              .setScale(4, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
            canTrex = bittrexCurrencies.result.exists(_.Currency == c.symbol),
            canPolo = poloniexCurrencies.get(c.symbol).isDefined
          )
        )
    }
  }

  def getHitBTCCurrencies: Future[Seq[HitBTCCurrenciesResponse]] = {
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
            BigDecimal(bitFlyerPrice.mid * c.last.toDouble)
              .setScale(4, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
            canBinance = binanceCurrencies.exists(_.symbol == c.symbol),
            canTrex = bittrexCurrencies.result.exists(_.Currency == c.symbol),
            canPolo = poloniexCurrencies.get(c.symbol).isDefined
          )
        )
    }
  }

  def getCryptopiaCurrencies: Future[Seq[CryptopiaCurrenciesResponse]] = {
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
            c.LastPrice.toString,
            BigDecimal(bitFlyerPrice.mid * c.LastPrice)
              .setScale(4, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
            canBinance = binanceCurrencies.exists(_.symbol == c.Label),
            canTrex = bittrexCurrencies.result.exists(_.Currency == c.Label),
            canPolo = poloniexCurrencies.get(c.Label).isDefined
          )
        )
    }
  }

  def getStocksCurrencies: Future[Seq[StocksCurrenciesResponse]] = {
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
            BigDecimal(bitFlyerPrice.mid * buy.toDouble)
              .setScale(4, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
            sell,
            BigDecimal(bitFlyerPrice.mid * sell.toDouble)
              .setScale(4, scala.math.BigDecimal.RoundingMode.HALF_UP).toString,
            canBinance = binanceCurrencies.exists(_.symbol == c.market_name),
            canTrex = bittrexCurrencies.result.exists(_.Currency == c.market_name),
            canPolo = poloniexCurrencies.get(c.market_name).isDefined
          )
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
    request("bitflyer.jp", HttpRequest(uri = "https://bitflyer.jp/api/echo/price")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestBitFlyerAPI", res)
        Future successful res.parseJson.convertTo[BitFlyerPrice]
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
}