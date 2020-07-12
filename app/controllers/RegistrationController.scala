package controllers

import javax.inject._
import models.{Registration, User}
import org.mindrot.jbcrypt.BCrypt
import play.api.mvc._
import play.api.Logger
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class RegistrationController @Inject()(val components: ControllerComponents,
                                       val mongoService: MongoService)
  extends AbstractController(components)
    with play.api.i18n.I18nSupport{

  def reInnit(): Action[AnyContent] = Action.async { implicit request:Request[AnyContent] =>
    mongoService.reInnitUsers.map( _ => Ok(views.html.message("Reinitialised collection with admin user")))
  }

  def addUser(username: String, email: String, password: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    mongoService.userEmailExists(email).flatMap {
      case true => Future.successful(Redirect(routes.RegistrationController.showRegistration()).withSession("registrationError" -> "error.emailInUse"))
      case false => mongoService.userUsernameExists(username).flatMap {
        case true => Future.successful(Redirect(routes.RegistrationController.showRegistration()).withSession("registrationError" -> "error.userInUse"))
        case false =>
          val passwordhash = BCrypt.hashpw(password, BCrypt.gensalt())
          val user = User(username, email, passwordhash)
          mongoService.addUser(user)
          Future.successful(Redirect(routes.HomeController.index()).withSession("user" -> user.username))
      }
    }
  }

  def showRegistration(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.registration(Registration.RegistrationForm))
  }

  def submitRegistration(): Action[AnyContent] = Action {implicit request: Request[AnyContent] =>
    Registration.RegistrationForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest(views.html.registration(formWithErrors))
    }, { register =>
      Redirect(routes.RegistrationController.addUser(register.username, register.email, register.password))

    })
  }
}
