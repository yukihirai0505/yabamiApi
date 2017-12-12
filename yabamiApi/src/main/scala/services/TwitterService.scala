package services

import com.danielasfregola.twitter4s.entities.Tweet
import configurations.TwitterConfig
import models.db.TweetsEntityTable
import models.{Page, TweetEntity, TweetResponseEntity}
import org.joda.time.DateTime
import com.github.tototoshi.slick.MySQLJodaSupport._

import scala.concurrent.{ExecutionContext, Future}

class TwitterService(val databaseService: DatabaseService)(implicit executionContext: ExecutionContext)
  extends TwitterConfig with TweetsEntityTable {

  import databaseService._
  import databaseService.driver.api._

  val ubiqannbot = "ubiqannbot"

  def savePastTweets(): Future[String] = {
    def loop(maxId: Option[Long] = None): Future[String] = {
      twitterClient.userTimelineForUser(screen_name = ubiqannbot, max_id = maxId).flatMap { r =>
        if (r.data.isEmpty) Future successful "ok"
        else saveTweets(r.data).flatMap(_ => loop(Some(r.data.last.id)))
      }
    }

    loop()
  }

  def saveCurrentTweets(): Future[String] = {
    twitterClient.userTimelineForUser(screen_name = ubiqannbot).flatMap { r =>
      saveTweets(r.data).flatMap(_ => Future successful "ok")
    }
  }

  def getTweets(word: Option[String], pageIndex: Option[Int], pageSize: Int = 30): Future[Page[TweetResponseEntity]] = {
    val offset: Int = pageIndex.getOrElse(0) * pageSize
    val query = tweets.filter(t => word.fold(true.bind)(w => t.text.like(s"%$w%")))
    db.run(query.length.result).flatMap { totalRowCount =>
      db.run(query
        .sortBy(_.createAt.desc)
        .drop(offset)
        .take(pageSize).result
      ).flatMap { d =>
        Future successful Page(
          list = d.map(t => TweetResponseEntity(t.id.toString, t.text, t.createAt)),
          pageIndex = pageIndex.getOrElse(0),
          pageSize = pageSize,
          totalRowCount = totalRowCount
        )
      }
    }
  }

  private def saveTweets(tweetsData: Seq[Tweet]) = {
    val tweetData = DBIO.sequence(
      tweetsData.map { tweet =>
        tweets.insertOrUpdate(
          TweetEntity(
            id = tweet.id,
            text = tweet.text,
            createAt = new DateTime(tweet.created_at)
          )
        )
      }
    )
    db.run(tweetData)
  }
}