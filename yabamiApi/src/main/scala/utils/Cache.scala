package utils

import net.sf.ehcache.{CacheManager, Element}

object CacheKey {
  val binance = "binance"
  val hitbtc = "hitbtc"
  val cryptopia = "cryptopia"
  val stocks = "stocks"
}

object Cache {
  lazy val cacheManager = CacheManager.getInstance()

  def put[T](key: String, value: T) = {
    val cache = cacheManager.getCache(key)
    cache.put(
      new Element(key, value)
    )
  }

  def get[T](key: String): Option[T] = {
    val cache = cacheManager.getCache(key)
    val value = cache.get(key)
    if (value != null) Some(value.getObjectValue.asInstanceOf[T])
    else None
  }
}