package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import connectors.APIConnector
import models.Battletag

import scala.concurrent.{ExecutionContext, Future}

class ProfileController @Inject()(val cc: ControllerComponents,
                                  val mongoService: MongoService,
                                  val connector: APIConnector)
  extends AbstractController(cc) with play.api.i18n.I18nSupport{

  implicit def ec: ExecutionContext = cc.executionContext

  def showForm(): Action[AnyContent] = Action { implicit request:Request[AnyContent] =>
    Ok(views.html.user(Battletag.BattletagForm))
  }

  def getProfileData(): Action[AnyContent] = Action.async {implicit request:Request[AnyContent] =>
    Battletag.BattletagForm.bindFromRequest().fold( formWithErrors =>
    Future.successful(BadRequest(views.html.user(formWithErrors))),
    battletag =>
    connector.getProfile(battletag.tag).map { res =>
      Ok(views.html.message(res))
    })
  }

}
