package controllers

import scala.io.Source
import connectors.PythonConnector
import javax.inject.Inject
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               val pythonConnector: PythonConnector)
  extends BaseController {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    if (request.session.get("user").isEmpty) {
      Ok(views.html.index()).withNewSession
    }
    else {
      Ok(views.html.index())
    }
  }

  def about(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.about())

  }

  def python(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    pythonConnector.callScript("Let's-throw-some-text-in-here")
    val file = Source.fromFile("test.txt")
    val text = file.getLines().mkString
    file.close()
    Ok(views.html.message(text))
  }
}
