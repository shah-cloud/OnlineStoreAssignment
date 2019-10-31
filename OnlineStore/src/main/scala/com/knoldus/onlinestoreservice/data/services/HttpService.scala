package com.knoldus.onlinestoreservice.data.services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.knoldus.onlinestoreservice.connection.MySQLImpl
import com.knoldus.onlinestoreservice.dashboard.util.ConfigProperties._
import com.knoldus.onlinestoreservice.routes.Routes

import scala.concurrent.ExecutionContextExecutor

trait HttpService extends Routes with DashboardComponent {

  implicit lazy val system: ActorSystem = ActorSystem()
  implicit lazy val materializer: ActorMaterializer = ActorMaterializer()
  implicit lazy val ec: ExecutionContextExecutor = system.dispatcher

  lazy val repo = new DashboardServices with MySQLImpl

  Http().bindAndHandle(route, httpHost, httpPort)

}
