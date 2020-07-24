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
    "competitiveStats": {
        "awards": {
            "cards": 4,
            "medals": 22,
            "medalsBronze": 7,
            "medalsSilver": 7,
            "medalsGold": 8
        },
        "games": {
            "played": 11,
            "won": 7
        }
    },
    "endorsement": 2,
    "endorsementIcon": "https://static.playoverwatch.com/svg/icons/endorsement-frames-3c9292c49d.svg#_2",
    "gamesWon": 350,
    "icon": "https://d15f34w2p8l1cc.cloudfront.net/overwatch/3fa38000f73344378c38cd735f621a829798e66c37e1fe8e135b25da9956e42c.png",
    "level": 27,
    "levelIcon": "https://d15f34w2p8l1cc.cloudfront.net/overwatch/4d63c2aadf536e87c84bdb7157c7b688cffb286e17a5362d2fa5c5281f4fc2a2.png",
    "name": "DPNoMNoM#2781",
    "prestige": 1,
    "prestigeIcon": "https://d15f34w2p8l1cc.cloudfront.net/overwatch/8de2fe5d938256a5725abe4b3655ee5e9067b7a1f4d5ff637d974eb9c2e4a1ea.png",
    "private": false,
    "quickPlayStats": {
        "awards": {
            "cards": 93,
            "medals": 844,
            "medalsBronze": 267,
            "medalsSilver": 283,
            "medalsGold": 294
        },
        "games": {
            "played": 319,
            "won": 163
        }
    },
    "rating": 2200,
    "ratingIcon": "https://d1u1mce87gyfbn.cloudfront.net/game/rank-icons/rank-GoldTier.png",
    "ratings": [
        {
            "level": 2195,
            "role": "tank",
            "roleIcon": "https://static.playoverwatch.com/img/pages/career/icon-tank-8a52daaf01.png",
            "rankIcon": "https://d1u1mce87gyfbn.cloudfront.net/game/rank-icons/rank-GoldTier.png"
        },
        {
            "level": 2206,
            "role": "support",
            "roleIcon": "https://static.playoverwatch.com/img/pages/career/icon-support-46311a4210.png",
            "rankIcon": "https://d1u1mce87gyfbn.cloudfront.net/game/rank-icons/rank-GoldTier.png"
        }
    ]
}"""
  }

}
