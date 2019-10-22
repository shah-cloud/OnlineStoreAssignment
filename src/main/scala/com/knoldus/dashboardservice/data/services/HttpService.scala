package com.knoldus.dashboardservice.data.services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.knoldus.dashboardservice.connection.HttpConnection.{httpHost, httpPort}
import com.knoldus.dashboardservice.connection.MySQLImpl
import com.knoldus.dashboardservice.routes.Routes

import scala.concurrent.ExecutionContextExecutor

trait HttpService extends DashboardComponent{

  implicit lazy val system: ActorSystem = ActorSystem()
  implicit lazy val materializer: ActorMaterializer = ActorMaterializer()
  implicit lazy val ec: ExecutionContextExecutor = system.dispatcher

  lazy val userRepo = new DashboardServices with MySQLImpl
  lazy val routes: Route = new Routes(userRepo).route

  Http().bindAndHandle(routes, httpHost, httpPort)

}
