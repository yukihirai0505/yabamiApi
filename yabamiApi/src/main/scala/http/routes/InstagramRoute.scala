package http.routes

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.CirceSupport
import io.circe.generic.auto._
import io.circe.syntax._
import services.InstagramService

import scala.concurrent.ExecutionContextExecutor

class InstagramRoute(instagramService: InstagramService
                    )(implicit ec: ExecutionContextExecutor) extends CirceSupport {

  import instagramService._

  val route = pathPrefix("instagram") {
    pathPrefix("users") {
      get {
        path(Remaining) { accountName =>
          complete(getUserInfo(accountName).map(_.asJson))
        }
      }
    }
  }

}
