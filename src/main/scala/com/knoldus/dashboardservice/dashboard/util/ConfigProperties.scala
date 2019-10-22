package com.knoldus.dashboardservice.dashboard.util

import com.typesafe.config.{Config, ConfigFactory}

object ConfigProperties {

  lazy val config: Config = ConfigFactory.load()

  lazy val httpHost: String = config.getConfig("http").getString("interface")
  lazy val httpPort: Int = config.getConfig("http").getInt("port")

}
