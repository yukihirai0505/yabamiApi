package models.db

import io.circe.{Decoder, Encoder, HCursor, Json}
import models.TweetEntity
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import services.DatabaseService

trait TweetsEntityTable {

  protected val databaseService: DatabaseService

  import com.github.tototoshi.slick.MySQLJodaSupport._
  import databaseService.driver.api._
  import slick.jdbc.{GetResult => GR}

  val datetimePattern = "yyyy-MM-dd HH:mm:ss"

  implicit val datetimeEncoder = new Encoder[DateTime] {
    final def apply(a: DateTime): Json =
      Json.fromString(a.toString(datetimePattern))
  }

  implicit val datetimeDecoder = new Decoder[DateTime] {
    final def apply(c: HCursor): Decoder.Result[DateTime] =
      c.as[String].right.map(DateTime.parse(_, DateTimeFormat.forPattern(datetimePattern)))
  }

  implicit def GetResultTweetEntity(implicit e0: GR[Long], e1: GR[String], e2: GR[DateTime]): GR[TweetEntity] = GR {
    prs =>
      import prs._
      val r = (<<[Long], <<[String], <<[DateTime])
      import r._
      TweetEntity.tupled((_1, _2, _3)) // putting AutoInc last
  }

  class Tweets(_tableTag: Tag) extends Table[TweetEntity](_tableTag, "tweets") {
    def * = (id, text, createAt) <> (TweetEntity.tupled, TweetEntity.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(text), Rep.Some(createAt)).shaped.<>({ r => import r._; _1.map(_ => TweetEntity.tupled((_1.get, _2.get, _3.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT), PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.PrimaryKey)
    /** Database column text SqlType(VARCHAR), Length(500,true) */
    val text: Rep[String] = column[String]("text", O.Length(500, varying = true))
    /** Database column create_at SqlType(DATETIME) */
    val createAt: Rep[DateTime] = column[DateTime]("create_at")
  }

  /** Collection-like TableQuery object for table Tweets */
  lazy val tweets = new TableQuery(tag => new Tweets(tag))
}
