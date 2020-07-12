package connectors

import javax.inject.Inject
import play.api.libs.json.JsValue
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class APIConnector @Inject()(ws: WSClient,
                             val cc: ControllerComponents) extends AbstractController(cc) {

  implicit def ec: ExecutionContext = cc.executionContext


  def getProfile(username: String) = {
    val url = s"https://ow-api.com/v1/stats/pc/eu/$username/profile"

    val request = ws.url(url)
      .withMethod("GET")
      .withRequestTimeout(10000.millis).get()

    val result = request.map { response =>
      response.json.toString()
    }

    result
    }


}
