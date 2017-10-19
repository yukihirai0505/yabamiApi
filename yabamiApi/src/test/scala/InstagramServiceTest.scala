import akka.actor.ActorSystem
import akka.http.scaladsl.testkit.RouteTestTimeout
import com.yukihirai0505.iService.responses.{AccountPostQuery, PageInfo, ProfileUserData, Tag}
import io.circe.generic.auto._
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Await
import scala.concurrent.duration._

class InstagramServiceTest extends BaseServiceTest with ScalaFutures {

  /**
    * if using dynamic variable route, this is necessary to complete test with success
    * ref: https://stackoverflow.com/questions/30977860/akka-http-route-test-request-was-neither-completed-nor-rejected-within-1-second
    *
    * @param system
    * @return
    */
  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(2.second)

  val targetAccountName = "i_do_not_like_fashion"
  val targetTagName = "idonotlikefashion"

  trait Context {
    val route = httpService.instagramRoute.route
  }

  "Instagram service" should {

    "retrieve user info" in new Context {
      Get(s"/instagram/users/$targetAccountName") ~> route ~> check {
        responseAs[Option[ProfileUserData]].isEmpty should be(false)
      }
    }

    "retrieve user posts paging" in new Context {
      val userInfo: ProfileUserData = Await.result(instagramService.getUserInfo(targetAccountName), Duration.Inf).get
      val pageInfo: PageInfo = userInfo.media.pageInfo
      Get(s"/instagram/users/${userInfo.id}/media/${pageInfo.endCursor.get}") ~> route ~> check {
        responseAs[Option[AccountPostQuery]].isEmpty should be(false)
      }
    }

    "retrieve tag info" in new Context {
      Get(s"/instagram/tags/$targetTagName") ~> route ~> check {
        responseAs[Option[Tag]].isEmpty should be(false)
      }
    }
  }

}
