package models

import org.joda.time.DateTime

case class TweetEntity(id: Long, text: String, createAt: DateTime)
case class TweetResponseEntity(id: String, text: String, createAt: DateTime)
