package models

import reactivemongo.bson.BSONObjectID

object User{
  def apply(username: String,
            email: String,
            password: String) = new User(BSONObjectID.generate(), username, email, password)
}

case class User(
               _id: BSONObjectID,
              username: String,
              email: String,
              password: String
               )
