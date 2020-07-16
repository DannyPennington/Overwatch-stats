package services

class StripperService {

}

object StripperService {

  def stripQuotes(text: String): String = {
    text.substring(1).dropRight(1)
  }
}
