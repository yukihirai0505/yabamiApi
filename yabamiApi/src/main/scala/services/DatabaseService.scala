package services

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

class DatabaseService(jdbcUrl: String, dbUser: String, dbPassword: String) {
  private val hikariConfig = new HikariConfig()
  hikariConfig.setJdbcUrl(jdbcUrl)
  hikariConfig.setUsername(dbUser)
  hikariConfig.setPassword(dbPassword)

  private val dataSource = new HikariDataSource(hikariConfig)

  val driver = slick.jdbc.MySQLProfile

  import driver.api._

  val db = Database.forDataSource(dataSource)
  db.createSession()
}
