package http.routes

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.CirceSupport
import io.circe.generic.auto._
import io.circe.syntax._
import services.TwitterService

import scala.concurrent.ExecutionContextExecutor

class TwitterRoute(twitterService: TwitterService
                  )(implicit ec: ExecutionContextExecutor) extends CirceSupport {

  import twitterService._

  val route = pathPrefix("twitter") {
    pathPrefix("tweets") {
      get {
        parameters('word.as[String].?, 'pageIndex.as[Int].?) { (word, pageIndex) =>
          complete(getTweets(word, pageIndex).map(_.asJson))
        }
      } ~
        post {
          complete(saveCurrentTweets().map(_.asJson))
        }
    }
  }

}
