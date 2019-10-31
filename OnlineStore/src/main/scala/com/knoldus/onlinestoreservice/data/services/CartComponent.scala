package com.knoldus.onlinestoreservice.data.services

import com.knoldus.onlinestoreservice.data.model.{ CartSchema, User }
import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ ForeignKeyQuery, ProvenShape }

trait CartComponent extends UsersComponent {

  val cart: TableQuery[Cart] = TableQuery[Cart]

  class Cart(tag: Tag) extends Table[CartSchema](tag, "CART") {

    def * : ProvenShape[CartSchema] = {
      (userId, itemNo, itemName, itemDetail, rating, price, vendorName, vendorContact, itemCategory, quantity).<>(CartSchema.tupled, CartSchema.unapply)
    }

    def userId: Rep[Int] = column[Int]("userId")

    def itemNo: Rep[Int] = column[Int]("itemNo")

    def itemName: Rep[String] = column[String]("itemName")

    def itemDetail: Rep[String] = column[String]("itemDetail")

    def rating: Rep[Double] = column[Double]("rating")

    def price: Rep[Double] = column[Double]("price")

    def vendorName: Rep[String] = column[String]("vendorName")

    def vendorContact: Rep[Long] = column[Long]("vendorContact")

    def itemCategory: Rep[String] = column[String]("itemCategory")

    def quantity: Rep[Int] = column[Int]("quantity")

    def userIdFk: ForeignKeyQuery[Users, User] = {
      foreignKey("USER_ID_FK", userId, users)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
    }

  }

}
