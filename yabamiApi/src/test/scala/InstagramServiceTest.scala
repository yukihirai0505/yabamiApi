import io.circe.generic.auto._
import models.SampleEntity
import org.scalatest.concurrent.ScalaFutures

class InstagramServiceTest extends BaseServiceTest with ScalaFutures {

  trait Context {
    val route = httpService.instagramRoute.route
  }

  "Sample service" should {

    "retrieve sample list" in new Context {
      Get("/sample") ~> route ~> check {
        responseAs[Seq[SampleEntity]].isEmpty should be(false)
      }
    }

  }

}
