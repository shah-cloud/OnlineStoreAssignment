package com.knoldus.onlinestoreservice.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.knoldus.onlinestoreservice.dashboard.util.JsonSupport
import com.knoldus.onlinestoreservice.data.model.{ Item, User }
import com.knoldus.onlinestoreservice.data.services.DashboardComponent

trait Routes extends JsonSupport with DashboardComponent {

  val repo: DashboardServices

  val route: Route =
    concat(
      path("addItem") {
        post {
          entity(as[Item]) { item =>
            onComplete(repo.insert(item)) {
              case util.Success(value) if value == 1 => complete("Item added successfully")
              case util.Success(value) => complete("Item is not added")
              case util.Failure(exception) => complete(exception)
            }
          }
        }
      },
      path("registeration") {
        post {
          entity(as[User]) { user =>
            onComplete(repo.insert(user)) {
              case util.Success(value) if value == 1 => onComplete(repo.authenticateUser(user.id, "no")) {
                case util.Success(value) if value == 1 =>
                  val id = user.id
                  val link = s"http://localhost:8080/verification-link/$id"
                  complete(s"To register go through the link\n Here is the link $link")
                case util.Success(value) => complete("Registeration is not done")
                case util.Failure(exception) => complete(exception)
              }
              case util.Success(value) => complete("Registeration is not done")
              case util.Failure(exception) => complete(exception)
            }
          }
        }
      },
      get {
        path("verification-link" / IntNumber) { userId =>
          onComplete(repo.authenticateUser(userId)) {
            case util.Success(res) if res.head._2 == "yes" =>
              val loginlink = s"http://localhost:8080/login-link/$userId"
              complete(s"Already Verified\nHere is your login link : $loginlink")
            case util.Success(res) =>
              onComplete(repo.verifyUser(userId, "yes")) {
                case util.Success(value) if value == 1 =>
                  val loginlink = s"http://localhost:8080/login-link/$userId"
                  complete(s"Successfully Verified\nHere is your login link : $loginlink")
                case util.Success(value) => complete("Verification is not done")
                case util.Failure(ex) => complete(ex)
              }
            case util.Failure(ex) => complete(ex)
          }
        }
      },
      get {
        path("login-link" / IntNumber) { userId =>
          onComplete(repo.authenticateUser(userId)) {
            case util.Success(res) if res.head._2 == "yes" =>
              val cartLink = s"http://localhost:8080/cart/$userId"
              val itemsLink = s"http://localhost:8080/items"
              val linkToAddItem = s"http://localhost:8080/addItemIntoCart/$userId"
              val profileLink = s"http://localhost:8080/user-profile/$userId"
              val editProfileLink = s"http://localhost:8080/edit-profile"
              val removeItemFromCartLink = s"http://localhost:8080/removeItemFromCart/$userId"
              val placeOrderLink = s"http://localhost:8080/place-order/$userId"
              complete(s"You have login successfully\nHere is your cart link : $cartLink\n"
                + s"Here is your items link : $itemsLink\n"
                + s"Here is the link to add item in cart : $linkToAddItem\n"
                + s"Here is your profile link : $profileLink\n"
                + s"Here is the link to edit profile : $editProfileLink\n"
                + s"Here is the link to remove item from cart : $removeItemFromCartLink\n"
                + s"Here is the link to place order : $placeOrderLink")
            case util.Success(res) =>
              val link = s"http://localhost:8080/verification-link/$userId"
              complete(s"Yet user not verify\nTo verify go through the link\n Here is the link $link")
            case util.Failure(ex) => complete(ex)
          }
        }
      },
      get {
        path("items-list") {
          complete {
            repo.getallItems
          }
        }
      },
      get {
        pathPrefix("sortByPrice" / Segment) { order =>
          complete(repo.sortPrice(order))
        }
      },
      get {
        pathPrefix("sortByRating" / Segment) { order =>
          complete(repo.sortRating(order))
        }
      },
      get {
        pathPrefix("sortByCategory" / Segment) { order =>
          complete(repo.sortCategory(order))
        }
      },
      get {
        path("cart" / IntNumber) { userId =>
          onComplete(repo.authenticateUser(userId)) {
            case util.Success(res) if res.head._2 == "yes" =>
              complete(repo.getCartItems(userId))
            case util.Success(res) =>
              val link = s"http://localhost:8080/verification-link/$userId"
              complete(s"Yet user not verify\nTo verify go through the link\n Here is the link $link")
            case util.Failure(ex) => complete(ex)
          }
        }
      },
      post {
        path("addItemIntoCart" / IntNumber / IntNumber) { (userId, quantity) =>
          entity(as[Item]) { item =>
            onComplete(repo.authenticateUser(userId)) {
              case util.Success(res) if res.head._2 == "yes" =>
                onComplete(repo.addItemIntoCart(userId, item, quantity)) {
                  case util.Success(value) if value == 1 =>
                    val cartlink = s"http://localhost:8080/cart/$userId"
                    complete(s"Added successfully\nHere is your cart link:\n $cartlink")
                  case util.Success(res) =>
                    val linkToAddItem = s"http://localhost:8080/addItemIntoCart/$userId"
                    complete(s"Item is not added successfully\nPlease try again\nHere is the link to add item in cart : $linkToAddItem")
                  case util.Failure(ex) => complete(ex)
                }
              case util.Success(res) =>
                val link = s"http://localhost:8080/verification-link/$userId"
                complete(s"Yet user not verify\nTo verify go through the link\n Here is the link $link")
              case util.Failure(ex) => complete(ex)
            }
          }
        }
      },
      get {
        path("removeItemFromCart" / IntNumber / IntNumber) { (userId, itemNo) =>
          onComplete(repo.authenticateUser(userId)) {
            case util.Success(res) if res.head._2 == "yes" =>
              onComplete(repo.removeItemFromCart(userId, itemNo)) {
                case util.Success(value) if value == 1 =>
                  val cartlink = s"http://localhost:8080/cart/$userId"
                  complete(s"Item removed successfully\nHere is your cart link:\n $cartlink")
                case util.Success(res) =>
                  val removeItemFromCartLink = s"http://localhost:8080/removeItemFromCart/$userId"
                  complete(s"Item is not removed successfully\nPlease try again\nHere is the link to remove item from cart : $removeItemFromCartLink")
                case util.Failure(ex) => complete(ex)
              }
            case util.Success(res) =>
              val link = s"http://localhost:8080/verification-link/$userId"
              complete(s"Yet user not verify\nTo verify go through the link\n Here is the link $link")
            case util.Failure(ex) => complete(ex)
          }
        }
      },
      path("edit-profile") {
        post {
          entity(as[User]) { user =>
            repo.updateUserProfile(user)
            val userId = user.id
            val profileLink = s"http://localhost:8080/user-profile/$userId"
            complete(s"Updated successfully\n Here is the link\n$profileLink")
          }
        }
      },
      get {
        path("user-profile" / IntNumber) { userId =>
          complete(repo.getUserProfile(userId))
        }
      },
      get {
        path("place-order" / IntNumber) { userId =>
          onComplete(repo.getClearCart(userId)) {
            case util.Success(value) if value == 1 =>
              val cartlink = s"http://localhost:8080/cart/$userId"
              complete(s"Order has placed successfully\nHere is your cart link:\n$cartlink")
            case util.Success(res) =>
              val placeOrderLink = s"http://localhost:8080/place-order/$userId"
              complete(s"Order has not placed successfully \nPlease try again\nHere is the link to place order : $placeOrderLink")
            case util.Failure(ex) => complete(ex)
          }
        }
      }
    )

}
