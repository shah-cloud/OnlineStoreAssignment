package com.knoldus.dashboardservice.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.knoldus.dashboardservice.dashboard.util.JsonSupport
import com.knoldus.dashboardservice.data.model.{Item, User}
import com.knoldus.dashboardservice.data.services.DashboardComponent


class Routes(repo: DashboardComponent#DashboardServices)
  extends JsonSupport {

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
            case util.Failure(ex) => complete("shah")
          }
        }
      },
      get {
        path("login-link" / IntNumber) { userId =>
          val cartlink = s"http://localhost:8080/cart/$userId"
          val itemslink = s"http://localhost:8080/items"
          val linkToAddItem = s"http://localhost:8080/addItemIntoCart/$userId"
          complete(s"You have login successfully\nHere is your cart link : $cartlink\nHere is your items link : $itemslink\n Here is the link to add item in cart : $linkToAddItem")
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
              complete("Item removed successfully")
            case util.Success(res) =>
              val link = s"http://localhost:8080/verification-link/$userId"
              complete(s"Yet user not verify\nTo verify go through the link\n Here is the link $link")
            case util.Failure(ex) => complete(ex)
          }
        }
      },
      path("vdata") {
        get {
          onComplete(repo.vall) {
            case util.Success(value) => complete("List is as follows " + value)
          }
          //            complete(repo.vall)
        }
      },
      path("userdata") {
        get {

          complete(repo.getAllUsers)
        }
      },
      path("update") {
        post {
          entity(as[User]) { user => // will unmarshal JSON to Order
            repo.update(user)
            complete("Updated successfully")
          }
        }
      }
    )

}
