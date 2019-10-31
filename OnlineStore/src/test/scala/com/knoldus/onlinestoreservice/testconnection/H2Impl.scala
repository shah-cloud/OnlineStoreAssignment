package com.knoldus.onlinestoreservice.testconnection

import com.knoldus.onlinestoreservice.connection.DB
import slick.jdbc.JdbcProfile

trait H2Impl extends DB {

  override val driver: JdbcProfile = slick.jdbc.H2Profile

  import driver.api._

  override val db: driver.backend.DatabaseDef = Database.forConfig("h2url")

}
