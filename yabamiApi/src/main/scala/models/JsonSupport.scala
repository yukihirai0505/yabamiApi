package models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.typesafe.scalalogging.LazyLogging
import spray.json.{DefaultJsonProtocol, JsValue}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol with LazyLogging {

  protected case class BitFlyerPrice(ltp: Double)

  protected case class CoincheckPrice(last: Double)

  protected case class ZaifPrice(last_price: Double)

  protected case class BtcToJpy(bitflyer: String, coincheck: String, zaif: String)
  protected case class JpyToBtc(bitflyer: String, coincheck: String, zaif: String)

  protected case class BinanceCurrency(symbol: String, price: String)

  protected case class BittrexCurrency(Currency: String, CurrencyLong: String)

  protected case class BittrexCurrencies(result: Seq[BittrexCurrency])

  protected case class HitBTCCurrency(symbol: String, last: Option[String])

  protected case class CryptopiaCurrency(Label: String, LastPrice: Option[Double])

  protected case class CryptopiaCurrencies(Data: Seq[CryptopiaCurrency])

  protected case class StocksCurrency(market_name: String, buy: JsValue, sell: JsValue)

  implicit val BitFlyerPriceFormat = jsonFormat1(BitFlyerPrice)
  implicit val CoincheckPriceFormat = jsonFormat1(CoincheckPrice)
  implicit val ZaifPriceFormat = jsonFormat1(ZaifPrice)
  implicit val BtcToJpyFormat = jsonFormat3(BtcToJpy)
  implicit val JpyToBtcFormat = jsonFormat3(JpyToBtc)
  implicit val BinanceCurrencyFormat = jsonFormat2(BinanceCurrency)
  implicit val BittrexCurrencyFormat = jsonFormat2(BittrexCurrency)
  implicit val BittrexCurrenciesFormat = jsonFormat1(BittrexCurrencies)
  implicit val HitBTCCurrencyFormat = jsonFormat2(HitBTCCurrency)
  implicit val CryptopiaCurrencyFormat = jsonFormat2(CryptopiaCurrency)
  implicit val CryptopiaCurrenciesFormat = jsonFormat1(CryptopiaCurrencies)
  implicit val StocksCurrencyFormat = jsonFormat3(StocksCurrency)
}
