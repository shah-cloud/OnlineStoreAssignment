package com.knoldus.onlinestoreservice.data.services

import com.knoldus.onlinestoreservice.data.model.User
import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ ForeignKeyQuery, ProvenShape }

trait VerifyUserComponent extends UsersComponent {

  val verifier: TableQuery[VerifyUser] = TableQuery[VerifyUser]

  class VerifyUser(tag: Tag) extends Table[(Int, String)](tag, "VERIFY_USER") {

    def * : ProvenShape[(Int, String)] = (userId, emailVerify)

    def emailVerify: Rep[String] = column[String]("emailVerify")

    def userId: Rep[Int] = column[Int]("userId", O.PrimaryKey)

    def userIdFk: ForeignKeyQuery[Users, User] = {
      foreignKey("USER_ID_FK", userId, users)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
    }

  }

}
