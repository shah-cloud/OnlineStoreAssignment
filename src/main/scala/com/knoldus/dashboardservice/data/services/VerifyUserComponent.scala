package com.knoldus.dashboardservice.data.services

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

trait VerifyUserComponent extends UsersComponent {

  class VerifyUser(tag: Tag) extends Table[(Int, String)](tag, "VERIFY") {

    def * : ProvenShape[(Int, String)] = (userId, emailVerify)

    def userId: Rep[Int] = column[Int]("userId", O.PrimaryKey)

    def emailVerify: Rep[String] = column[String]("emailVerify")

    def userIdFk = foreignKey("USER_ID_FK", userId, users)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

  }

  val verifier = TableQuery[VerifyUser]

}
