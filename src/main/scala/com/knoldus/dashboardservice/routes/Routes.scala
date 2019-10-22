package com.knoldus.dashboardservice.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.knoldus.dashboardservice.dashboard.util.JsonSupport
import com.knoldus.dashboardservice.data.model.{Item, User}
import com.knoldus.dashboardservice.data.services.DashboardComponent


class Routes(repo: DashboardComponent#DashboardServices) extends JsonSupport {

  val route: Route =
    concat(
      path("addItem") {
        post {
          entity(as[Item]) { item => // will unmarshal JSON to Order
            repo.insert(item)
            complete(s"Item added successfully")
          }
        }
      },
      path("registeration") {
        post {
          entity(as[User]) { user => // will unmarshal JSON to Order
            repo.insert(user)
            repo.authenticateUser(user.id, "no")
            val id = user.id
            val link = s"http://localhost:8080/verification-link/$id"
            complete(s"To register go through the link\n Here is the link $link")
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
              repo.verifyUser(userId, "yes")
              val loginlink = s"http://localhost:8080/login-link/$userId"
              complete(s"Successfully Verified\nHere is your login link : $loginlink")
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
                + s"Here is the link to place order : $placeOrderLink"
              )
            case util.Success(res) =>
              val link = s"http://localhost:8080/verification-link/$userId"
              complete(s"Yet user not verify\nTo verify go through the link\n Here is the link $link")
            case util.Failure(ex) => complete(ex)
          }
        }
      },
      get {
        path("items") {
          complete(repo.getallItems)
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
                repo.addItemIntoCart(userId, item, quantity)
                val cartlink = s"http://localhost:8080/cart/$userId"
                complete(s"Added successfully\nHere is your cart link:\n $cartlink")
              case util.Success(res) =>
                val link = s"http://localhost:8080/verification-link/$userId"
                complete(s"Yet user not verify\nTo verify go through the link\n Here is the link $link")
              case util.Failure(ex) => complete(ex)
            }
          }
        }
      },
      get {
        path("removeItemFromCart" / IntNumber / IntNumber) { (userId, itemNo) => // will unmarshal JSON to Order

          onComplete(repo.authenticateUser(userId)) {
            case util.Success(res) if res.head._2 == "yes" =>
              repo.removeItem(userId, itemNo)
              val cartlink = s"http://localhost:8080/cart/$userId"
              complete(s"Item removed successfully\nHere is your cart link:\n $cartlink")
            case util.Success(res) =>
              val link = s"http://localhost:8080/verification-link/$userId"
              complete(s"Yet user not verify\nTo verify go through the link\n Here is the link $link")
            case util.Failure(ex) => complete(ex)
          }
        }
      },
      path("verifierdata") {
        get {
          complete(repo.vall)
        }
      },
      path("removeVerifierdata") {
        get {
          repo.getClearVerivierData
          complete("Cleared Successfully")
        }
      },
      path("userdata") {
        get {
          complete(repo.getAllUsers)
        }
      },
      path("edit-profile") {
        post {
          entity(as[User]) { user => // will unmarshal JSON to Order
            repo.update(user)
            complete("Updated successfully")
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
          repo.getClearCart(userId)
          val cartlink = s"http://localhost:8080/cart/$userId"
          complete(s"Order placed successfully\nHere is your cart link:\n$cartlink")
        }
      }
    )

}
