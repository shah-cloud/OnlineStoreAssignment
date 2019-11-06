package com.knoldus.onlinestoreservice.services

import com.knoldus.onlinestoreservice.connection.DB
import com.knoldus.onlinestoreservice.dashboard.util.LoggerUtil.logger
import com.knoldus.onlinestoreservice.model.{CartSchema, Item, User}
import play.api.libs.json.JsError
import play.api.libs.json.JsResult.Exception
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

trait DashboardComponent extends VerifyUserComponent with CartComponent with ItemsComponent {

  class DashboardServices(implicit ec: ExecutionContext = ExecutionContext.global) {
    this: DB =>

    def getUserProfile(userID: Int): Future[Seq[User]] = db.run {
      logger.info("Getting users profile corresponding to a given userId")
      users.filter(_.id === userID).result
    }

    def getallItems: Future[Seq[Item]] = db.run {
      logger.info("Getting all items from an Items table")
      items.result
    }

    def getCartItems(userId: Int): Future[Seq[CartSchema]] = db.run {
      logger.info("Getting all items of user-cart from an Cart table")
      cart.filter(_.userId === userId).result
    }

    def insert(user: User): Future[Int] = {
      logger.info("Inserting a user credentials to an Users table")
      db.run(DBIO.seq(
        users.schema.createIfNotExists))
      db.run(users += user)
    }

    def insert(item: Item): Future[Int] = {
      logger.info("Inserting a new item to an Items table")
      db.run(DBIO.seq(
        items.schema.createIfNotExists))
      db.run(items += item)
    }

    def addItemIntoCart(userId: Int, item: Item, quantity: Int): Future[Int] = {
      logger.info("Inserting a new item to a user-cart")
      val cartItem: CartSchema = Item.unapply(item) match {
        case Some(a) => a match {
          case (itemNo, itemName, itemDetail, rating, price, vendorName, vendorContact, itemCategory) =>
            CartSchema(userId, itemNo, itemName, itemDetail, rating, price, vendorName, vendorContact, itemCategory, quantity)
          case _ => throw Exception(JsError("addItemIntoCart method has invalid argument 'item'"))
        }
      }
      db.run(
        DBIO.seq(
          cart.schema.createIfNotExists))
      db.run(cart += cartItem)
    }

    def updateUserProfile(user: User): Future[Int] = db.run {
      logger.info("Updating a user credentials to an Users table")
      users.filter(_.id === user.id).map(_.name).update(user.name) andThen
        users.filter(_.id === user.id).map(_.emailId).update(user.emailId) andThen
        users.filter(_.id === user.id).map(_.phoneNo).update(user.phoneNo)
    }

    def authenticateUser(userId: Int, verification: String): Future[Int] = {
      logger.info("Inserting userId and verification string \"no\" in VerifyUser table ")
      db.run(DBIO.seq(
        verifier.schema.createIfNotExists))
      db.run(verifier += (userId, verification))
    }

    def authenticateUser(id: Int): Future[Seq[(Int, String)]] = db.run {
      logger.info("Authenticating user , registered or not")
      verifier.filter(_.userId === id).result
    }

    def verifyUser(id: Int, verification: String): Future[Int] = db.run {
      logger.info("Updating verification string with \"yes\" in VerifyUser table corresponding to a given userId")
      verifier.filter(_.userId === id).map(_.emailVerify).update(verification)
    }

    def removeItemFromCart(userId: Int, itemNo: Int): Future[Int] = db.run {
      logger.info("Removing an item from a user-cart corresponding to a given userId and itemNo")
      cart.filter(value => value.itemNo === itemNo && value.userId === userId).delete
    }

    def getClearCart(userId: Int): Future[Int] = db.run {
      logger.info("Deleting all items from a user-cart corresponding to a given userId")
      cart.filter(_.userId === userId).delete
    }

    def sortPrice(order: String): Future[Seq[Item]] = db.run {
      logger.info(s"Getting all items sorted by price from an Items table according ta an given string $order")
      order match {
        case "asc" => items.sortBy(_.price.asc.nullsFirst).result
        case "desc" => items.sortBy(_.price.desc.nullsLast).result
      }
    }

    def sortRating(order: String): Future[Seq[Item]] = db.run {
      logger.info(s"Getting all items sorted by rating from an Items table according ta an given string $order")
      order match {
        case "asc" => items.sortBy(_.rating.asc.nullsFirst).result
        case "desc" => items.sortBy(_.rating.desc.nullsLast).result
      }
    }

    def sortCategory(order: String): Future[Seq[Item]] = db.run {
      logger.info(s"Getting all items sorted by category from an Items table according ta an given string $order")
      order match {
        case "asc" => items.sortBy(_.itemCategory.asc.nullsFirst).result
        case "desc" => items.sortBy(_.itemCategory.desc.nullsLast).result
      }
    }

  }

}
