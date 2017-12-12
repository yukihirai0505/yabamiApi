import akka.actor.ActorSystem
import akka.http.scaladsl.testkit.RouteTestTimeout
import io.circe.generic.auto._
import models.TweetEntity
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.duration._

class TwitterServiceTest extends BaseServiceTest with ScalaFutures { // this import is necessary for unmarshalling

  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(10.second)

  trait Context {
    val route = httpService.twitterRoute.route
  }

  "twitter service" should {

    "retrieve tweets" in new Context {
      Get(s"/twitter/tweets") ~> route ~> check {
        responseAs[Seq[TweetEntity]].isEmpty should be(false)
      }
    }

  }

}
