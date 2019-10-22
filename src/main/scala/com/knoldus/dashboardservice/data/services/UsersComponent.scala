package com.knoldus.dashboardservice.data.services

import com.knoldus.dashboardservice.data.model.User
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

trait UsersComponent {

  val users = TableQuery[Users]

  class Users(tag: Tag) extends Table[User](tag, "USERS") {


    def * : ProvenShape[User] = (id, name, emailId, phoneNo).<>(User.tupled, User.unapply)

    def id: Rep[Int] = column[Int]("id", O.PrimaryKey)

    def phoneNo: Rep[Double] = column[Double]("phoneNo")

    def name: Rep[String] = column[String]("name")

    def emailId: Rep[String] = column[String]("emailId")

  }

}
