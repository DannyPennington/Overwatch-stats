package models

import java.time.LocalDateTime

import play.api.libs.json.{JsValue, Json, OFormat}

case class Stats(username: String, compScore: Int, wins: Int, gamesPlayed: Int, dateTime: LocalDateTime = LocalDateTime.now()) {

  def toJson: JsValue = Json.parse(
    s"""{
      | "User": $username,
      | "Score": $compScore,
      | "Wins": $wins,
      | "Total games": $gamesPlayed,
      | "Timestamp": "${dateTime.toString}"
      | }
      |""".stripMargin)
}

object Stats {
  implicit val formats: OFormat[Stats] = Json.format[Stats]
}
