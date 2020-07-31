package controllers

import javax.inject.Inject
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

class HomeController @Inject()(val controllerComponents: ControllerComponents)
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
}
