package models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.typesafe.scalalogging.LazyLogging
import spray.json.{DefaultJsonProtocol, JsValue}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol with LazyLogging {

  protected case class BitFlyerPrice(ask: Double, bid: Double, mid: Double)

  protected case class BinanceCurrency(symbol: String, price: String)

  protected case class BittrexCurrency(Currency: String, CurrencyLong: String)

  protected case class BittrexCurrencies(result: Seq[BittrexCurrency])

  protected case class HitBTCCurrency(
                                       symbol: String,
                                       last: String,
                                       open: String,
                                       low: String,
                                       high: String,
                                       volume: String,
                                       volumeQuote: String
                                     )

  protected case class CryptopiaCurrency(Label: String, LastPrice: Double)
  protected case class CryptopiaCurrencies(Data: Seq[CryptopiaCurrency])

  protected case class StocksCurrency(market_name: String, buy: JsValue, sell: JsValue)

  implicit val BitFlyerPriceFormat = jsonFormat3(BitFlyerPrice)
  implicit val BinanceCurrencyFormat = jsonFormat2(BinanceCurrency)
  implicit val BittrexCurrencyFormat = jsonFormat2(BittrexCurrency)
  implicit val BittrexCurrenciesFormat = jsonFormat1(BittrexCurrencies)
  implicit val HitBTCCurrencyFormat = jsonFormat7(HitBTCCurrency)
  implicit val CryptopiaCurrencyFormat = jsonFormat2(CryptopiaCurrency)
  implicit val CryptopiaCurrenciesFormat = jsonFormat1(CryptopiaCurrencies)
  implicit val StocksCurrencyFormat = jsonFormat3(StocksCurrency)
}
