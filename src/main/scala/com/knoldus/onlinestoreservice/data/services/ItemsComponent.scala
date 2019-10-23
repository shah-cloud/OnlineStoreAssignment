package com.knoldus.onlinestoreservice.data.services

import com.knoldus.onlinestoreservice.data.model.Item
import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ProvenShape, QueryBase}

trait ItemsComponent {

  val items: TableQuery[Items] = TableQuery[Items]

  class Items(tag: Tag) extends Table[Item](tag, "ITEMS") {

    def * : ProvenShape[Item] = (itemNo, itemName, itemDetail, rating, price, vendorName, vendorContact, itemCategory).<>(Item.tupled, Item.unapply)

    def itemNo: Rep[Int] = column[Int]("itemNo", O.PrimaryKey)

    def itemName: Rep[String] = column[String]("itemName")

    def itemDetail: Rep[String] = column[String]("itemDetail")

    def rating: Rep[Double] = column[Double]("rating")

    def price: Rep[Double] = column[Double]("price")

    def vendorName: Rep[String] = column[String]("vendorName")

    def vendorContact: Rep[Long] = column[Long]("vendorContact")

    def itemCategory: Rep[String] = column[String]("itemCategory")

  }

}
