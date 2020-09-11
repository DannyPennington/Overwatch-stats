package models

import play.api.libs.json.{JsValue, OFormat}

object JsonFormats {

  import play.api.libs.json.Json
  import reactivemongo.play.json._
  import reactivemongo.play.json.collection.JSONCollection

  implicit val userFormat:OFormat[User] = Json.format[User]
}
