package controllers

import javax.inject._

import models.Login
import org.mindrot.jbcrypt.BCrypt
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class LoginController @Inject()(val components: ControllerComponents, val mongoService: MongoService) extends AbstractController(components) with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def logout():Action[AnyContent] = Action {implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.index()).withNewSession
  }

  def showLogin(err: String = ""): Action[AnyContent] = Action {implicit request: Request[AnyContent] =>
    Ok(views.html.login(Login.LoginForm))
  }

  def submitLogin(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Login.LoginForm.bindFromRequest.fold({ formWithErrors =>
      Future.successful(BadRequest(views.html.login(formWithErrors)))
    }, { login =>
      if (mongoService.isEmail(login.account)) {
        mongoService.findUserEmail(login.account).map {
          case user@Some(_) =>
            if (BCrypt.checkpw(login.password, user.head.password)) {
              Redirect(routes.HomeController.index()).withSession("user" -> user.get.username)
            }
            else {
              Redirect(routes.LoginController.showLogin()).withSession("loginError" -> "error.emailIncorrect")
            }
          case _ => Redirect(routes.LoginController.showLogin()).withSession("loginError" -> "error.emailIncorrect")
        }
      }
      else {
        mongoService.findUserUsername(login.account).map {
          case user@Some(_) =>
            if (BCrypt.checkpw(login.password, user.head.password)) {
              Redirect(routes.HomeController.index()).withSession("user" -> user.get.username)
            }
            else {
              Redirect(routes.LoginController.showLogin()).withSession("loginError" -> "error.userIncorrect")
            }
          case _ => Redirect(routes.LoginController.showLogin()).withSession("loginError" -> "error.userIncorrect")
        }
      }
    }
    )
  }
}
