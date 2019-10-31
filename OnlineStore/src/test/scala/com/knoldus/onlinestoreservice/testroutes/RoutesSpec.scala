package com.knoldus.onlinestoreservice.testroutes

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.knoldus.onlinestoreservice.dashboard.util.JsonSupport
import com.knoldus.onlinestoreservice.data.model.{CartSchema, Item, User}
import com.knoldus.onlinestoreservice.data.services.DashboardComponent
import com.knoldus.onlinestoreservice.routes.Routes
import com.knoldus.onlinestoreservice.testconstant.TestConstants.{testCartItem, testItem1, testItem2, testUser}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.Future

class RoutesSpec extends WordSpec with Matchers with ScalatestRouteTest with MockitoSugar with JsonSupport with Routes with DashboardComponent {

  val repo = mock[DashboardServices]

  "Post request to /addItem" should {
    "return \"Item added successfully\" as response" in {
      when(repo.insert(testItem1)).thenReturn(Future.successful(1))

      val jsonRequest = ByteString(
        s"""
           |{
           |    "itemCategory": "clothes",
           |    "itemDetail": "pant",
           |    "itemName": "blackpant",
           |    "itemNo": 4,
           |    "price": 1000.75,
           |    "rating": 4.3,
           |    "vendorContact": 1234567890,
           |    "vendorName": "A.Singh"
           |}
        """.stripMargin)

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/addItem",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

      postRequest ~> Route.seal(route) ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[String] === "Item added successfully")
      }
    }
  }

  "Post request to /registeration" should {
    "return a verification link as response" in {
      when(repo.insert(testUser)).thenReturn(Future.successful(1))
      when(repo.authenticateUser(1, "no")).thenReturn(Future.successful(1))

      val jsonRequest = ByteString(
        s"""
           |{
           |    "id": 1,
           |    "name": "shah",
           |    "emailId": "shah@gmail.com",
           |    "phoneNo": 817121
           |}
        """.stripMargin)

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/registeration",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

      postRequest ~> Route.seal(route) ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[String] === "To register go through the link\n Here is the link http://localhost:8080/verification-link/1")
      }
    }
  }

  "/verification-link route" should {
    "returns login link if user already verified" in {
      when(repo.authenticateUser(1)).thenReturn(Future[Seq[(Int, String)]](Seq((1, "yes"))))

      Get("/verification-link/1") ~> route ~> check {
        assert(responseAs[String] === "Already Verified\nHere is your login link : http://localhost:8080/login-link/1")
      }
    }
  }

  "/verification-link route" should {
    "returns login link and update the emailVerify column corresponding to the given user id " in {
      when(repo.authenticateUser(1)).thenReturn(Future[Seq[(Int, String)]](Seq((1, "no"))))
      when(repo.verifyUser(1, "yes")).thenReturn(Future.successful(1))

      Get("/verification-link/1") ~> route ~> check {
        assert(responseAs[String] === "Successfully Verified\nHere is your login link : http://localhost:8080/login-link/1")
      }
    }
  }

  "/login-link route" should {
    "returns all the utility links corresponding to the given user id " in {
      when(repo.authenticateUser(1)).thenReturn(Future[Seq[(Int, String)]](Seq((1, "yes"))))

      Get("/login-link/1") ~> route ~> check {
        assert(responseAs[String] === "You have login successfully\nHere is your cart link : http://localhost:8080/cart/1\nHere is your items link : http://localhost:8080/items\nHere is the link to add item in cart : http://localhost:8080/addItemIntoCart/1\nHere is your profile link : http://localhost:8080/user-profile/1\nHere is the link to edit profile : http://localhost:8080/edit-profile\nHere is the link to remove item from cart : http://localhost:8080/removeItemFromCart/1\nHere is the link to place order : http://localhost:8080/place-order/1")
      }
    }
  }

  "/login-link route when verifiction is not done" should {
    "returns verification link and message \"Yet user not verify\" corresponding to the given user id " in {
      when(repo.authenticateUser(1)).thenReturn(Future[Seq[(Int, String)]](Seq((1, "no"))))
      Get("/login-link/1") ~> route ~> check {
        assert(responseAs[String] === "Yet user not verify\nTo verify go through the link\n Here is the link http://localhost:8080/verification-link/1")
      }

    }
  }

  "/items-list route" should {
    "returns all items " in {
      when(repo.getallItems).thenReturn(Future[Seq[Item]](Seq(testItem1)))

      Get("/items-list") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[Vector[Item]] === Vector(testItem1))
      }
    }
  }

  "/sortByPrice/asc route" should {
    "returns all items according their price in ascending order" in {
      when(repo.sortPrice("asc")).thenReturn(Future[Seq[Item]](Seq(testItem1, testItem2)))

      Get("/sortByPrice/asc") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[Vector[Item]] === Vector(testItem1, testItem2))
      }
    }
  }

  "/sortByPrice/desc route" should {
    "returns all items according their price in descending order" in {
      when(repo.sortPrice("desc")).thenReturn(Future[Seq[Item]](Seq(testItem2, testItem1)))

      Get("/sortByPrice/desc") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[Vector[Item]] === Vector(testItem2, testItem1))
      }
    }
  }

  "/sortByRating/asc route" should {
    "returns all items according their rating in ascending order" in {
      when(repo.sortRating("asc")).thenReturn(Future[Seq[Item]](Seq(testItem1, testItem2)))

      Get("/sortByRating/asc") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[Vector[Item]] === Vector(testItem1, testItem2))
      }
    }
  }

  "/sortByRating/desc route" should {
    "returns all items according their rating in descending order" in {
      when(repo.sortRating("desc")).thenReturn(Future[Seq[Item]](Seq(testItem2, testItem1)))

      Get("/sortByRating/desc") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[Vector[Item]] === Vector(testItem2, testItem1))
      }
    }
  }

  "/sortByCategory/asc route" should {
    "returns all items according their category in ascending order" in {
      when(repo.sortCategory("asc")).thenReturn(Future[Seq[Item]](Seq(testItem1, testItem2)))

      Get("/sortByCategory/asc") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[Vector[Item]] === Vector(testItem1, testItem2))
      }
    }
  }

  "/sortByCategory/desc route" should {
    "returns all items according their category in descending order" in {
      when(repo.sortCategory("desc")).thenReturn(Future[Seq[Item]](Seq(testItem2, testItem1)))

      Get("/sortByCategory/desc") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[Vector[Item]] === Vector(testItem2, testItem1))
      }
    }
  }

  "/cart/1 route" should {
    "returns all items from cart corresponding to the given user id" in {
      when(repo.authenticateUser(1)).thenReturn(Future[Seq[(Int, String)]](Seq((1, "yes"))))
      when(repo.getCartItems(1)).thenReturn(Future[Seq[CartSchema]](Seq(testCartItem)))

      Get("/cart/1") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[Vector[CartSchema]] === Vector(testCartItem))
      }
    }
  }

  "/cart/1 route when user verification is not done" should {
    "returns verification link and message \"Yet user not verify\" corresponding to the given user id " in {
      when(repo.authenticateUser(1)).thenReturn(Future[Seq[(Int, String)]](Seq((1, "no"))))

      Get("/cart/1") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[String] === "Yet user not verify\nTo verify go through the link\n Here is the link http://localhost:8080/verification-link/1")
      }
    }
  }

  "Post request to /addItemIntoCart/1/5 route" should {
    "returns cart link as response and add item into cart with quantity corresponding to the given user id" in {
      when(repo.authenticateUser(1)).thenReturn(Future[Seq[(Int, String)]](Seq((1, "yes"))))
      when(repo.addItemIntoCart(1, testItem1, 5)).thenReturn(Future.successful(1))

      val jsonRequest = ByteString(
        s"""
           |{
           |    "itemCategory": "clothes",
           |    "itemDetail": "pant",
           |    "itemName": "blackpant",
           |    "itemNo": 4,
           |    "price": 1000.75,
           |    "rating": 4.3,
           |    "vendorContact": 1234567890,
           |    "vendorName": "A.Singh"
           |}
        """.stripMargin)

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/addItemIntoCart/1/5",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

      postRequest ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[String] === "Added successfully\nHere is your cart link:\n http://localhost:8080/cart/1")
      }
    }
  }

  "Post request to /addItemIntoCart/1/5 route when user verification is not done" should {
    "returns verification link and message \"Yet user not verify\" as response corresponding to the given user id" in {
      when(repo.authenticateUser(1)).thenReturn(Future[Seq[(Int, String)]](Seq((1, "no"))))

      val jsonRequest = ByteString(
        s"""
           |{
           |    "itemCategory": "clothes",
           |    "itemDetail": "pant",
           |    "itemName": "blackpant",
           |    "itemNo": 4,
           |    "price": 1000.75,
           |    "rating": 4.3,
           |    "vendorContact": 1234567890,
           |    "vendorName": "A.Singh"
           |}
        """.stripMargin)

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/addItemIntoCart/1/5",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

      postRequest ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[String] === "Yet user not verify\nTo verify go through the link\n Here is the link http://localhost:8080/verification-link/1")
      }
    }
  }

  "/removeItemFromCart/1/4 route" should {
    "returns cart link as response and remove item from cart corresponding to the given user id and itemNo" in {
      when(repo.authenticateUser(1)).thenReturn(Future[Seq[(Int, String)]](Seq((1, "yes"))))
      when(repo.removeItemFromCart(1, 4)).thenReturn(Future.successful(1))

      Get("/removeItemFromCart/1/4") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[String] === "Item removed successfully\nHere is your cart link:\n http://localhost:8080/cart/1")
      }
    }
  }

  "/removeItemFromCart/1/4 route when user verification is not done" should {
    "returns verification link and message \"Yet user not verify\" as response corresponding to the given user id" in {
      when(repo.authenticateUser(1)).thenReturn(Future[Seq[(Int, String)]](Seq((1, "no"))))

      Get("/removeItemFromCart/1/4") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[String] === "Yet user not verify\nTo verify go through the link\n Here is the link http://localhost:8080/verification-link/1")
      }
    }
  }

  "/user-profile/1 route" should {
    "returns all items " in {
      when(repo.getUserProfile(1)).thenReturn(Future[Seq[User]](Seq(testUser)))

      Get("/user-profile/1") ~> route ~> check {
        status.isSuccess() shouldEqual true
        assert(responseAs[Vector[User]] === Vector(testUser))
      }
    }
  }

  "/place-order/1 route" should {
    "placed the order successfully and return the cart link as response" in {
      when(repo.getClearCart(1)).thenReturn(Future.successful(1))

      Get("/place-order/1") ~> route ~> check {
        assert(responseAs[String] === "Order has placed successfully\nHere is your cart link:\nhttp://localhost:8080/cart/1")
      }
    }
  }

  "/place-order/1 route when order has not placed successfully" should {
    "return the place-order link as response" in {
      when(repo.getClearCart(1)).thenReturn(Future[Int](0))

      Get("/place-order/1") ~> route ~> check {
        assert(responseAs[String] === "Order has not placed successfully \nPlease try again\nHere is the link to place order : http://localhost:8080/place-order/1")
      }
    }
  }

}
