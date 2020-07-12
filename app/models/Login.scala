package models
import play.api.data.Form
import play.api.data.Forms._

case class Login(account: String, password: String)

object Login {
  val LoginForm:Form[Login] = Form(
    mapping(
      "account" -> nonEmptyText,
      "password" -> nonEmptyText
    )(Login.apply)(Login.unapply)
  )
}
