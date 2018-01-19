package models.db

import services.DatabaseService

/**
  * Created by Yuky on 2018/01/19.
  */
trait CMCDataEntityTable {
  protected val databaseService: DatabaseService

  import databaseService.driver.api._
  import slick.jdbc.{GetResult => GR}

  case class CmcDataRow(id: String, name: String, symbol: String, rank: String, priceBtc: Option[String] = None, availableSupply: Option[String] = None, totalSupply: Option[String] = None, maxSupply: Option[String] = None, percentChange1h: Option[String] = None, percentChange24h: Option[String] = None, percentChange7d: Option[String] = None, lastUpdated: Option[String] = None)

  implicit def GetResultCmcDataRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[CmcDataRow] = GR {
    prs =>
      import prs._
      val r = (<<[String], <<[String], <<[String], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String])
      import r._
      CmcDataRow.tupled((_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12)) // putting AutoInc last
  }

  class CmcData(_tableTag: Tag) extends Table[CmcDataRow](_tableTag, "cmc_data") {
    def * = (id, name, symbol, rank, priceBtc, availableSupply, totalSupply, maxSupply, percentChange1h, percentChange24h, percentChange7d, lastUpdated) <> (CmcDataRow.tupled, CmcDataRow.unapply)

    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(symbol), Rep.Some(rank), priceBtc, availableSupply, totalSupply, maxSupply, percentChange1h, percentChange24h, percentChange7d, lastUpdated).shaped.<>({ r => import r._; _1.map(_ => CmcDataRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6, _7, _8, _9, _10, _11, _12))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    val id: Rep[String] = column[String]("id", O.PrimaryKey, O.Length(50, varying = true))
    val name: Rep[String] = column[String]("name", O.Length(50, varying = true))
    val symbol: Rep[String] = column[String]("symbol", O.Length(50, varying = true))
    val rank: Rep[String] = column[String]("rank", O.Length(100, varying = true))
    val priceBtc: Rep[Option[String]] = column[Option[String]]("price_btc", O.Length(500, varying = true), O.Default(None))
    val availableSupply: Rep[Option[String]] = column[Option[String]]("available_supply", O.Length(500, varying = true), O.Default(None))
    val totalSupply: Rep[Option[String]] = column[Option[String]]("total_supply", O.Length(500, varying = true), O.Default(None))
    val maxSupply: Rep[Option[String]] = column[Option[String]]("max_supply", O.Length(500, varying = true), O.Default(None))
    val percentChange1h: Rep[Option[String]] = column[Option[String]]("percent_change_1h", O.Length(45, varying = true), O.Default(None))
    val percentChange24h: Rep[Option[String]] = column[Option[String]]("percent_change_24h", O.Length(45, varying = true), O.Default(None))
    val percentChange7d: Rep[Option[String]] = column[Option[String]]("percent_change_7d", O.Length(45, varying = true), O.Default(None))
    val lastUpdated: Rep[Option[String]] = column[Option[String]]("last_updated", O.Length(45, varying = true), O.Default(None))
  }

  lazy val cmcData = new TableQuery(tag => new CmcData(tag))

}
