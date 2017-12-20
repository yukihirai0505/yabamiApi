package http

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import http.routes._
import services._
import utils.CorsSupport

import scala.concurrent.ExecutionContextExecutor

class HttpService(
                   instagramService: InstagramService,
                   twitterService: TwitterService,
                   tradeService: TradeService
                 )(implicit ec: ExecutionContextExecutor, actorSystem: ActorSystem, materializer: ActorMaterializer) extends CorsSupport {

  val instagramRoute = new InstagramRoute(instagramService)
  val twitterRoute = new TwitterRoute(twitterService)
  val tradeRoute = new TradeRoute(tradeService)

  val routes =
    pathPrefix("v1") {
      corsHandler {
        instagramRoute.route ~
          twitterRoute.route ~
          tradeRoute.route
      }
    }

}
