package models
import play.api.data.Form
import play.api.data.Forms._

case class Battletag(tag: String)

object Battletag {

  val BattletagForm: Form[Battletag] = Form(
    mapping(
      "tag" -> nonEmptyText,
    )(Battletag.apply)(Battletag.unapply)
  )

}
