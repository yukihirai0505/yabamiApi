import akka.actor.ActorSystem
import akka.http.scaladsl.testkit.RouteTestTimeout
import io.circe.generic.auto._
import models.{Page, TweetEntity}
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.duration._


class TradeServiceTest extends BaseServiceTest with ScalaFutures {

  // this import is necessary for unmarshalling
  import tradeService._

  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(10.second)

  trait Context {
    val route = httpService.tradeRoute.route
  }

  "trade service" should {

    "retrieve currencies" in new Context {
      Get(s"/currencies") ~> route ~> check {
        responseAs[Seq[CurrencyResponse]].isEmpty should be(false)
      }
    }

  }

}
