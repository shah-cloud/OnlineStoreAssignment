package com.knoldus.dashboardservice.data.services

import com.knoldus.dashboardservice.connection.DB
import com.knoldus.dashboardservice.data.model.{CartSchema, Item, User}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

trait DashboardComponent extends VerifyUserComponent with CartComponent with ItemsComponent {

  class DashboardServices(implicit ec: ExecutionContext) {
    this: DB =>


    def getAllUsers = db.run {
      users.result
    }

    def getUserProfile(userID: Int) = db.run {
      users.filter(_.id === userID).result
    }

    def getClearVerivierData = db.run {
      DBIO.seq(
        verifier.schema.dropIfExists
      )
    }

    def vall = db.run {
      verifier.result
    }

    def getallItems = db.run {
      items.result
    }

    def getCartItems(userId: Int) = db.run {
      cart.filter(_.userId === userId).result
    }

    def insert(user: User) = db.run {
      DBIO.seq(
        users.schema.createIfNotExists,
        users += user
      )
    }

    def insert(item: Item) = db.run{
      DBIO.seq(
        items.schema.createIfNotExists,
        items += item
      )
    }

    def addItemIntoCart(uId: Int, item: Item, q: Int) = {
      val a: CartSchema = Item.unapply(item) match {
        case Some(a) => a match {
          case (a, b, c, d, e, f, g, h) => CartSchema(uId, a, b, c, d, e, f, g, h, q)
        }
      }
      db.run(
        DBIO.seq(
          cart.schema.createIfNotExists,
          cart += a
        )
      )
    }

    def update(user: User) = {
      val cName = users.filter(_.id === user.id).map(_.name)
      val cEmail = users.filter(_.id === user.id).map(_.emailId)
      val cPh = users.filter(_.id === user.id).map(_.phoneNo)

      db.run(DBIO.seq(
        cName.update(user.name), cEmail.update(user.emailId), cPh.update(user.phoneNo)
      ))
    }

    def authenticateUser(id: Int, verification: String) = db.run {
      DBIO.seq(
        verifier.schema.createIfNotExists,
        verifier += (id, verification),
      )
    }

    def authenticateUser(id: Int) = db.run {
      verifier.filter(x => x.userId === id).result
    }


    def verifyUser(id: Int, verification: String) = db.run{
      verifier.filter(_.userId === id).map(_.emailVerify).update(verification)
    }

    def removeItem(userId: Int, itemNo: Int) = db.run{
      cart.filter(value => value.itemNo === itemNo && value.userId === userId).delete
    }

    def getClearCart(userId: Int) = db.run{
      cart.filter(_.userId === userId).delete
    }

    def sortPrice(order: String): Future[Seq[Item]] = db.run {
      order match {
        case "asc" => items.sortBy(_.price.asc.nullsFirst).result
        case "desc" => items.sortBy(_.price.desc.nullsLast).result
      }
    }

    def sortRating(order: String): Future[Seq[Item]] = db.run {
      order match {
        case "asc" => items.sortBy(_.rating.asc.nullsFirst).result
        case "desc" => items.sortBy(_.rating.desc.nullsLast).result
      }
    }

    def sortCategory(order: String): Future[Seq[Item]] = db.run {
      order match {
        case "asc" => items.sortBy(_.itemCategory.asc.nullsFirst).result
        case "desc" => items.sortBy(_.itemCategory.desc.nullsLast).result
      }
    }

  }

}
