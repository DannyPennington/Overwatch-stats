package services

class StripperService {

}

object StripperService {

  def stripQuotes(text: String): String = {
    text.replaceAll("""['"]""", "")
  }
}
