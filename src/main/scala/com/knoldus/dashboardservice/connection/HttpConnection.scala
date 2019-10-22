package com.knoldus.dashboardservice.connection

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.{ Config, ConfigFactory }

import scala.concurrent.ExecutionContextExecutor

trait HttpConnection {

  lazy val config: Config = ConfigFactory.load()
  implicit lazy val system: ActorSystem = ActorSystem()
  implicit lazy val materializer: ActorMaterializer = ActorMaterializer()
  implicit lazy val ec: ExecutionContextExecutor = system.dispatcher

  lazy val httpHost: String = config.getConfig("http").getString("interface")
  lazy val httpPort: Int = config.getConfig("http").getInt("port")

}

object HttpConnection extends HttpConnection
