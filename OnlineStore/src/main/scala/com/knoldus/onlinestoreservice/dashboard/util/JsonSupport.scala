package com.knoldus.onlinestoreservice.dashboard.util

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.knoldus.onlinestoreservice.model.{CartSchema, Item, User}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val itemFormat: RootJsonFormat[User] = jsonFormat4(User)
  implicit val orderFormat: RootJsonFormat[Item] = jsonFormat8(Item)
  implicit val cartFormat: RootJsonFormat[CartSchema] = jsonFormat10(CartSchema)

}
