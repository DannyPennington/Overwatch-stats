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

  /*
  def python(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    pythonConnector.callScript("Let's-throw-some-text-in-here")
    val file = Source.fromFile("test.txt")
    val text = file.getLines().mkString
    file.close()
    Ok(views.html.message(text))
  }
  */

  def image(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val arg1 = "3"
    val arg2 = "6"

    pythonConnector.callScript(arg1, arg2)
    val path = s"images/1_$arg1,2_$arg2.png"

    Ok(views.html.image(path))
  }


}
