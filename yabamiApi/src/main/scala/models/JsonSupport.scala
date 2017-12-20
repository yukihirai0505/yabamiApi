package models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.typesafe.scalalogging.LazyLogging
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol with LazyLogging {

  protected case class BinanceCurrency(symbol: String, price: String)

  protected case class BittrexCurrency(Currency: String, CurrencyLong: String)

  protected case class BittrexCurrencies(result: Seq[BittrexCurrency])

  implicit val BinanceCurrencyFormat = jsonFormat2(BinanceCurrency)
  implicit val BittrexCurrencyFormat = jsonFormat2(BittrexCurrency)
  implicit val BittrexCurrenciesFormat = jsonFormat1(BittrexCurrencies)
}
