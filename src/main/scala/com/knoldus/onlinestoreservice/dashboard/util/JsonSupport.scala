package com.knoldus.onlinestoreservice.dashboard.util

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.knoldus.onlinestoreservice.data.model.{CartSchema, Item, User}
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val itemFormat = jsonFormat4(User)
  implicit val orderFormat = jsonFormat8(Item)
  implicit val cartFormat = jsonFormat10(CartSchema)

}
