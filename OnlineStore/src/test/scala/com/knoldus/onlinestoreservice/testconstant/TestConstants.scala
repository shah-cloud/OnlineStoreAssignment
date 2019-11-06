package com.knoldus.onlinestoreservice.testconstant

import com.knoldus.onlinestoreservice.model.{CartSchema, Item, User}
import com.knoldus.onlinestoreservice.services.DashboardComponent
import com.knoldus.onlinestoreservice.testconnection.H2Impl

object TestConstants extends DashboardComponent {

  val testRepoObj: DashboardServices = new DashboardServices with H2Impl

  val testItem1 = Item(4, "blackpant", "pant", 4.3, 1000.75, "A.Singh", 1234567890, "clothes")
  val testItem2 = Item(5, "blackpant", "pant", 5.3, 1500, "A.Singh", 1234567890L, "woolen clothes")
  val testUser = User(1, "shah", "shah@gmail.com", 817121)
  val testCartItem = CartSchema(testUser.id, 4, "blackpant", "pant", 4.3, 1000.75, "A.Singh", 1234567890L, "clothes", 5)

}
