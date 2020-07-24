package models

import java.time.LocalDateTime

import play.api.libs.json.{JsValue, Json, OFormat}

case class Stats(compScore: Int, winLoss: Int, gamesPlayed: Int, dateTime: LocalDateTime = LocalDateTime.now()) {

  def toJson: JsValue = Json.parse(
    s"""{
      | "Score": $compScore,
      | "Win ratio": $winLoss,
      | "Total games": $gamesPlayed,
      | "Timestamp": "${dateTime.toString}"
      | }
      |""".stripMargin)
}

object Stats {
  implicit val formats: OFormat[Stats] = Json.format[Stats]
}
