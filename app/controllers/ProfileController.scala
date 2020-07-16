package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import connectors.APIConnector
import models.Battletag
import play.api.libs.json.{JsValue, Json}

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
    battletag => Future.successful(Ok(views.html.profile(testJson))) // TEMPORARILY USING TEST JSON TO AVOID SPAM CALLING API WHILE DEVELOPING
    //connector.getProfile(battletag.tag).map { res =>
    //  Ok(views.html.profile(res))
    )
  }

  val testJson: JsValue = Json.parse{"""{
    "icon": "https://d15f34w2p8l1cc.cloudfront.net/overwatch/3fa38000f73344378c38cd735f621a829798e66c37e1fe8e135b25da9956e42c.png",
    "name": "DPNoMNoM",
    "level": 100,
    "levelIcon": "https://blzgdapipro-a.akamaihd.net/game/playerlevelrewards/0x0250000000000951_Border.png",
    "prestige": 2,
    "prestigeIcon": "https://blzgdapipro-a.akamaihd.net/game/playerlevelrewards/0x0250000000000951_Rank.png",
    "rating": "9999",
    "ratingIcon": "https://blzgdapipro-a.akamaihd.net/game/rank-icons/season-2/rank-7.png",
    "gamesWon": 734
    }"""
  }

}
