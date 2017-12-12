package configurations

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

trait Config extends LazyLogging {
  def config = LoadedConfig.config
}

object LoadedConfig {
  lazy val config = ConfigFactory.load
}