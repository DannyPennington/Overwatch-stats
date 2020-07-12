package controllers

import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class RegistrationControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar{
  val mongoService: MongoService = mock[MongoService]
  "RegistrationController" must {
    "display the registration form on the page" in {
      val controller = new RegistrationController(stubControllerComponents(),mongoService)
      val request = controller.showRegistration().apply(FakeRequest(GET, "/register"))
      // Just showing the 2 different syntaxes for "must be" assertions, both perform the same function
      status(request) mustBe OK
      contentType(request) must be(Some("text/html"))
      contentAsString(request) must include ("<form action=\"/register\" method=\"POST\" >")
    }
  }


}
