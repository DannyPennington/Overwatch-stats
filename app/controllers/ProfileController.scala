package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request, Result}
import connectors.APIConnector
import models.{Battletag, Stats}
import play.api.libs.json.{JsValue, Json}
import services.StripperService.stripQuotes
import play.api.Configuration

import scala.concurrent.{ExecutionContext, Future}

class ProfileController @Inject()(configuration: Configuration,
                                  val cc: ControllerComponents,
                                  val mongoService: MongoService,
                                  val connector: APIConnector)
  extends AbstractController(cc) with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = cc.executionContext

  def showForm(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.user(Battletag.BattletagForm))
  }

  def getProfileData(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Battletag.BattletagForm.bindFromRequest().fold(formWithErrors =>
      Future.successful(BadRequest(views.html.user(formWithErrors))),
      battletag =>
        showProfileData(battletag)
    )
  }

  def showProfileData(battletag: Battletag)(implicit request: Request[AnyContent]): Future[Result] = {
    connector.getProfile(battletag.tag).map { jsonResponse =>
      if (configuration.underlying.getBoolean("behavior.stub")) {saveStats(jsonResponse)}
      var count: Int = 0
      if (jsonResponse.\("ratings").\(0).isDefined) { count += 1 }
      if (jsonResponse.\("ratings").\(1).isDefined) { count += 1 }
      if (jsonResponse.\("ratings").\(2).isDefined) { count += 1 }

      val nums = Array.range(0, count)

      Ok(views.html.profile(jsonResponse, nums))
    }
  }

  def saveStats(data: JsValue): Unit = {
    val totalGames: Int = data.\("competitiveStats").get.\("games").get.\("played").get.toString.toInt
    val wins: Int = data.\("competitiveStats").get.\("games").get.\("won").get.toString.toInt
    val stats = Stats(
      stripQuotes(data.\("name").get.toString()),
      stripQuotes(data.\("rating").get.toString()).toInt,
      wins,
      totalGames)

    mongoService.addStats(stats)
  }

}