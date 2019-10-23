package com.knoldus.onlinestoreservice.testconstant

import com.knoldus.onlinestoreservice.connection.DB
import com.knoldus.onlinestoreservice.data.model.{CartSchema, Item, User}
import com.knoldus.onlinestoreservice.data.services.{DashboardComponent, HttpService}
import com.knoldus.onlinestoreservice.testconnection.H2Impl

object TestConstants extends DashboardComponent with HttpService{
  this: DB =>

  val testObj: DashboardServices = new DashboardServices with H2Impl

  val testItem1: Item =
    Item(124, "Branded Things", "From a branded company most trusted and good working", 3.0, 3500.0, "Shahfahed", (2346584832L), "Electronics")
  val testItem2: Item =
    Item(98, "Branded Earphones", "From a branded company most trusted and good working", 5.0, 2500.0, "Shahfahed", 2346584832L, "Electronics")
  val testItem3: Item =
    Item(99, "Branded Shirt", "From a branded company most trusted and good working", 4.0, 500.0, "Unknown", 3462584833L, "Shirts")
  val testUser: User =
    User(124,"shah","shah@gmail.com",935802271)
  val testCartItem: CartSchema =
    CartSchema(testUser.id, 124, "Branded Things", "From a branded company most trusted and good working", 3.0, 3500.0, "Shahfahed", (2346584832L), "Electronics",5)

}
