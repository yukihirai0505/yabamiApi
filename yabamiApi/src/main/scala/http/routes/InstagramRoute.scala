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
        } ~ path(Segment / "media") { userId =>
          parameters('afterCode.as[String].?) { afterCode =>
            complete(getUserPostsPaging(userId, afterCode.getOrElse("")).map(_.asJson))
          }
        }
      }
    } ~ pathPrefix("tags") {
      get {
        path(Segment) { tagName =>
          complete(getTagInfo(decode(tagName)).map(_.asJson))
        } ~ path(Segment / "media") { tagName =>
          parameters('afterCode.as[String].?) { afterCode =>
            complete(getTagPostsPaging(decode(tagName), afterCode.getOrElse("")).map(_.asJson))
          }
        } ~ path("recommend" / Segment) { tagName =>
          complete(getRecommendHashTag(tagName).map(_.asJson))
        }
      }
    } ~ pathPrefix("media") {
      get {
        path("shortcode" / Segment) { shortcode =>
          complete(getMediaInfo(shortcode).map(_.asJson))
        } ~ path("shortcode" / Segment / "comments") { shortcode =>
          parameters('afterCode.as[String].?) { afterCode =>
            complete(getCommentPaging(shortcode, afterCode.getOrElse("")))
          }
        } ~ path("shortcode" / Segment / "likes") { shortcode =>
          parameters('afterCode.as[String].?) { afterCode =>
            complete(getLikePaging(shortcode, afterCode.getOrElse("")))
          }
        }
      }
    }
  }

  private def decode(str: String) = {
    URLDecoder.decode(str, "UTF-8")
  }
}
