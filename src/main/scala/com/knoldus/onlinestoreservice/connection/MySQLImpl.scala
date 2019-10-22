package com.knoldus.onlinestoreservice.connection

import slick.jdbc.JdbcProfile

trait MySQLImpl extends DB {
  val driver: JdbcProfile = slick.jdbc.MySQLProfile

  import driver.api._

  val db: driver.backend.DatabaseDef = Database.forConfig("mysql")
}
