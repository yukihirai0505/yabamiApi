package configurations

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken}

trait TwitterConfig extends Config {

  private lazy val twitterConfig = config.getConfig("twitter")
  private val consumerConfig = twitterConfig.getConfig("consumer")
  private val consumerKey: String = consumerConfig.getString("key")
  private val consumerSecret: String = consumerConfig.getString("secret")

  private val accessConfig = twitterConfig.getConfig("access")
  private val accessKey: String = accessConfig.getString("key")
  private val accessSecret: String = accessConfig.getString("secret")
  private val consumerToken: ConsumerToken = ConsumerToken(consumerKey, consumerSecret)
  private val accessToken: AccessToken = AccessToken(accessKey, accessSecret)

  val twitterClient = new TwitterRestClient(consumerToken, accessToken)
}
