package http.routes

import java.net.URLDecoder

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
        path(Segment) { accountName =>
          complete(getUserInfo(accountName).map(_.asJson))
        } ~ path(Segment / "media" / Segment) { (userId, afterCode) =>
          complete(getUserPostsPaging(userId, afterCode).map(_.asJson))
        }
      }
    } ~ pathPrefix("tags") {
      get {
        path(Segment) { tagName =>
          complete(getTagInfo(URLDecoder.decode(tagName, "UTF-8")).map(_.asJson))
        }
      }
    }
  }

}
