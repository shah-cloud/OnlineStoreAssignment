package com.knoldus.onlinestoreservice.testservice

import com.knoldus.onlinestoreservice.testconstant.TestConstants._
import org.scalatest.AsyncWordSpec
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{ Millis, Seconds, Span }

class DashboardServicesSpec extends AsyncWordSpec with ScalaFutures {

  implicit def defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(4, Seconds), interval = Span(500, Millis))

  "insert new item method " should {
    "add the items" in {
      val response = testObj.insert(testItem1)
      whenReady(response) { y =>
        assert(y === 1)
      }
    }
  }

  "insert new user method " should {
    "add the user" in {
      val response = testObj.insert(testUser)
      whenReady(response) { y =>
        assert(y === 1)
      }
    }
  }

  "getUserProfile method" should {
    "returns user profile corresponding to the given user id" in {
      testObj.insert(testUser)
      val response = testObj.getUserProfile(testUser.id)
      whenReady(response) { profile =>
        assert(profile === Vector(testUser))
      }
    }
  }

  "getallItems method" should {
    "returns all items" in {
      testObj.insert(testItem2)
      testObj.insert(testItem3)
      val response = testObj.getallItems
      whenReady(response) { items =>
        assert(items === Vector(testItem1,testItem2,testItem3))
      }
    }
  }

  "addItemIntoCart method" should {
    "add the item corresponding to the given user id and quantity" in {
      val response = testObj.addItemIntoCart(testUser.id, testItem1, 5)
      whenReady(response) { value =>
        assert(value === 1)
      }
    }
  }

  "getCartItems method" should {
    "returns containing items corresponding to the given user id" in {
      val response = testObj.getCartItems(testUser.id)
      whenReady(response) { items =>
        assert(items === Vector(testCartItem))
      }
    }
  }

  "authenticateUser method with parameters userId and verification string" should {
    "put the userId with verification string in Verifyuser table" in {
      val response = testObj.authenticateUser(testUser.id, "no")
      whenReady(response) { value =>
        assert(value === 1)
      }
    }
  }

  "authenticateUser method with parameters userId" should {
    "return the details corresponding to the given user id" in {
      val response = testObj.authenticateUser(testUser.id)
      whenReady(response) { verify =>
        assert(verify === Vector((124,"no")))
      }
    }
  }

  "updateUserProfile method" should {
    "update the user profile" in {
      val response = testObj.updateUserProfile(testUser)
      whenReady(response) { value =>
        assert(value === 1)
      }
    }
  }

  "verifyUser method" should {
    "update the verifyUser table" in {
      val response = testObj.verifyUser(testUser.id,"yes")
      whenReady(response) { value =>
        assert(value === 1)
      }
    }
  }

//  "removeItemFromCart method" should {
//    "remove the item from Cart table table" in {
//      testObj.addItemIntoCart(testUser.id, testItem1, 5)
//      testObj.addItemIntoCart(testUser.id, testItem2, 5)
//      val response = testObj.removeItemFromCart(testUser.id, testItem1.itemNo)
//      whenReady(response) { value =>
//        assert(value === 1)
//      }
//    }
//  }
}
