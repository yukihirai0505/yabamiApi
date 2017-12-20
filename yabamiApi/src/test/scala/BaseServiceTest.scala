import Main.{instagramService, twitterService}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.CirceSupport
import http.HttpService
import org.scalatest.{Matchers, WordSpec}
import services._
import utils.Config


/**
  * author Yuki Hirai on 2017/06/27.
  */
trait BaseServiceTest extends WordSpec with Matchers with ScalatestRouteTest with CirceSupport with Config {
  private val databaseService = new DatabaseService(jdbcUrl, dbUser, dbPassword)
  val twitterService = new TwitterService(databaseService)
  val instagramService = new InstagramService()
  val tradeService = new TradeService()

  val httpService = new HttpService(
    instagramService,
    twitterService,
    tradeService
  )

}
