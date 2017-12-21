package http.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.CirceSupport
import io.circe.generic.auto._
import io.circe.syntax._
import services.TradeService

import scala.concurrent.ExecutionContextExecutor

class TradeRoute(tradeService: TradeService
                )(implicit ec: ExecutionContextExecutor, actorSystem: ActorSystem) extends CirceSupport {

  import tradeService._

  val route = pathPrefix("currencies") {
    get {
      pathPrefix("binance") {
        complete(getBinanceCurrencies.map(_.asJson))
      } ~ pathPrefix("hitbtc") {
        complete(getHitBTCCurrencies.map(_.asJson))
      } ~ pathPrefix("cryptopia") {
        complete(getCryptopiaCurrencies.map(_.asJson))
      } ~ pathPrefix("stocks") {
        complete(getStocksCurrencies.map(_.asJson))
      }
    }
  }

}
