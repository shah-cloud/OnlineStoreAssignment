package com.knoldus.dashboardservice.connection

import slick.jdbc.JdbcProfile

trait DB {
  val driver: JdbcProfile

  val db: driver.backend.DatabaseDef
}
