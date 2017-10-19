import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.CirceSupport
import http.HttpService
import org.scalatest.{Matchers, WordSpec}
import services._


/**
  * author Yuki Hirai on 2017/06/27.
  */
trait BaseServiceTest extends WordSpec with Matchers with ScalatestRouteTest with CirceSupport {
  val instagramService = new InstagramService()
  val httpService = new HttpService(
    instagramService
  )

}
