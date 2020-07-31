package models
import play.api.data.Form
import play.api.data.Forms._

case class Registration(username: String, email: String, password: String)

object Registration {

  val RegistrationForm: Form[Registration] = Form(
    mapping(
      "username" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText
    )(Registration.apply)(Registration.unapply)
  )
}
