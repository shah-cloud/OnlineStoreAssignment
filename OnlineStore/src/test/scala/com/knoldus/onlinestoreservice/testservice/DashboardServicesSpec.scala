package com.knoldus.onlinestoreservice.testservice

import com.knoldus.onlinestoreservice.testconstant.TestConstants._
import org.scalatest.AsyncWordSpec
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

class DashboardServicesSpec extends AsyncWordSpec with ScalaFutures {

  implicit def defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(4, Seconds), interval = Span(500, Millis))

  "insert new item method " should {
    "add the items" in {
      val response = testRepoObj.insert(testItem1)
      whenReady(response) { y =>
        assert(y === 1)
      }
    }
  }

  "insert new user method " should {
    "add the user" in {
      val response = testRepoObj.insert(testUser)
      whenReady(response) { y =>
        assert(y === 1)
      }
    }
  }

  "getUserProfile method" should {
    "returns user profile corresponding to the given user id" in {
      testRepoObj.insert(testUser)
      val response = testRepoObj.getUserProfile(testUser.id)
      whenReady(response) { profile =>
        assert(profile === Vector(testUser))
      }
    }
  }

  "getallItems method" should {
    "returns all items" in {
      testRepoObj.insert(testItem2)
      val response = testRepoObj.getallItems
      whenReady(response) { items =>
        assert(items === Vector(testItem1, testItem2))
      }
    }
  }

  "addItemIntoCart method" should {
    "add the item corresponding to the given user id and quantity" in {
      val response = testRepoObj.addItemIntoCart(testUser.id, testItem1, 5)
      whenReady(response) { value =>
        assert(value === 1)
      }
    }
  }

  "getCartItems method" should {
    "returns containing items corresponding to the given user id" in {
      val response = testRepoObj.getCartItems(testUser.id)
      whenReady(response) { items =>
        assert(items === Vector(testCartItem))
      }
    }
  }

  "authenticateUser method with parameters userId and verification string" should {
    "put the userId with verification string in Verifyuser table" in {
      val response = testRepoObj.authenticateUser(testUser.id, "no")
      whenReady(response) { value =>
        assert(value === 1)
      }
    }
  }

  "authenticateUser method with parameters userId" should {
    "return the details corresponding to the given user id" in {
      val response = testRepoObj.authenticateUser(testUser.id)
      whenReady(response) { verify =>
        assert(verify === Vector((1, "no")))
      }
    }
  }

  "updateUserProfile method" should {
    "update the user profile" in {
      val response = testRepoObj.updateUserProfile(testUser)
      whenReady(response) { value =>
        assert(value === 1)
      }
    }
  }

  "verifyUser method" should {
    "update the verifyUser table" in {
      val response = testRepoObj.verifyUser(testUser.id, "yes")
      whenReady(response) { value =>
        assert(value === 1)
      }
    }
  }

  "sortPrice method with value asc" should {
    "returns items in ascending order" in {
      val response = testRepoObj.sortPrice("asc")
      whenReady(response) { items =>
        assert(items === Vector(testItem1, testItem2))
      }
    }
  }

  "sortPrice method with value desc" should {
    "returns items in descending order" in {
      val response = testRepoObj.sortPrice("desc")
      whenReady(response) { items =>
        assert(items === Vector(testItem2, testItem1))
      }
    }
  }

  "sortRating method with value asc" should {
    "returns items in ascending order" in {
      val response = testRepoObj.sortRating("asc")
      whenReady(response) { items =>
        assert(items === Vector(testItem1, testItem2))
      }
    }
  }

  "sortRating method with value desc" should {
    "returns items in descending order" in {
      val response = testRepoObj.sortRating("desc")
      whenReady(response) { items =>
        assert(items === Vector(testItem2, testItem1))
      }
    }
  }

  "sortCategory method with value asc" should {
    "returns items in ascending order" in {
      val response = testRepoObj.sortCategory("asc")
      whenReady(response) { items =>
        assert(items === Vector(testItem1, testItem2))
      }
    }
  }

  "sortCategory method with value desc" should {
    "returns items in descending order" in {
      val response = testRepoObj.sortCategory("desc")
      whenReady(response) { items =>
        assert(items === Vector(testItem2, testItem1))
      }
    }
  }

}
