import akka.actor.ActorSystem
import akka.http.scaladsl.testkit.RouteTestTimeout
import com.yukihirai0505.iService.responses.ProfileUserData
import io.circe.generic.auto._
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.duration._

class InstagramServiceTest extends BaseServiceTest with ScalaFutures {
  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(2.second)

  val targetAccountName = "i_do_not_like_fashion"

  trait Context {
    val route = httpService.instagramRoute.route
  }

  "Instagram service" should {

    "retrieve user info" in new Context {
      Get(s"/instagram/users/$targetAccountName") ~> route ~> check {
        responseAs[Option[ProfileUserData]].isEmpty should be(false)
      }
    }

  }

}
