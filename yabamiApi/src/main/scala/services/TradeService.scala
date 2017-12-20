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
  val BNB_STR = "BNB"

  case class CurrencyResponse(name: String, price: String, yen: String, canTrex: Boolean, canPolo: Boolean)

  def getCurrencies: Future[Seq[CurrencyResponse]] = {
    requestBinanceAPI.flatMap { binanceCurrencies =>
      requestBittrexAPI.flatMap { bittrexCurrencies =>
        requestPoloniexAPI.flatMap { poloniexCurrencies =>
          requestBitFlyerAPI.flatMap { bitFlyerPrice =>
            Future successful binanceCurrencies.map(c =>
              CurrencyResponse(
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
      }
    }
  }

  private def requestBinanceAPI = {
    request("api.binance.com", HttpRequest(uri = "https://api.binance.com/api/v1/ticker/allPrices")).flatMap { response =>
      Unmarshal(response.entity).to[String].flatMap { res =>
        logger.info("requestBinanceAPI", res)
        Future successful res.parseJson.convertTo[Seq[BinanceCurrency]]
          .filter(c => c.symbol.contains(BTC_STR) && !c.symbol.contains(BNB_STR))
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