import akka.actor.ActorSystem
import akka.http.scaladsl.testkit.RouteTestTimeout
import com.yukihirai0505.iService.responses._
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
  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(10.second)

  val targetAccountName = "i_do_not_like_fashion"
  val targetTagName = "idonotlikefashion"
  var targetShortcode = "BaczO1-BOdy"

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
      Get(s"/instagram/users/${userInfo.id}/media?afterCode=${pageInfo.endCursor.get}") ~> route ~> check {
        responseAs[Option[UserPostQuery]].isEmpty should be(false)
      }
    }

    "retrieve tag info" in new Context {
      Get(s"/instagram/tags/$targetTagName") ~> route ~> check {
        responseAs[Option[Tag]].isEmpty should be(false)
      }
    }

    "retrieve tag posts paging" in new Context {
      val hasManyPostsHashTag = "like4like"
      val tagInfo: Tag = Await.result(instagramService.getTagInfo(hasManyPostsHashTag), Duration.Inf).get
      val pageInfo = tagInfo.media.pageInfo
      Get(s"/instagram/tags/$hasManyPostsHashTag/media?afterCode=${pageInfo.endCursor.get}") ~> route ~> check {
        responseAs[Option[MediaQuery]].isEmpty should be(false)
      }
    }

    "retrieve media info" in new Context {
      Get(s"/instagram/media/shortcode/$targetShortcode") ~> route ~> check {
        responseAs[Option[PostPageGraphql]].isEmpty should be(false)
      }
    }

    "retrieve comments paging" in new Context {
      val hasManyCommentsShortCode = "BacSjodhmaZ"
      val mediaInfo: PostPageGraphql = Await.result(instagramService.getMediaInfo(hasManyCommentsShortCode), Duration.Inf).get
      val pageInfo = mediaInfo.shortcodeMedia.edgeMediaToComment.pageInfo
      Get(s"/instagram/media/shortcode/$hasManyCommentsShortCode/comments?afterCode=${pageInfo.endCursor.get}") ~> route ~> check {
        responseAs[Option[MediaCommentQuery]].isEmpty should be(false)
      }
    }

  }

}
