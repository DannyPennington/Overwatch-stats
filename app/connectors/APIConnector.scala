package connectors

import javax.inject.Inject
import play.api.Configuration
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class APIConnector @Inject()(configuration: Configuration,
                             ws: WSClient,
                             val cc: ControllerComponents) extends AbstractController(cc) {

  implicit def ec: ExecutionContext = cc.executionContext


  def getProfile(username: String): Future[JsValue] = {
    if (configuration.underlying.getBoolean("behavior.stub")) {
      Future.successful(testJson)
    }
    else {
      val url = s"https://ow-api.com/v1/stats/pc/eu/$username/profile"
      val request = ws.url(url)
        .withMethod("GET")
        .withRequestTimeout(10000.millis).get()

      val result = request.map { response =>
        response.json
      }
      result
    }
  }

  val testJson: JsValue = Json.parse {
    """{
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
