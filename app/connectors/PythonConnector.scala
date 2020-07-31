package connectors

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import sys.process._

class PythonConnector @Inject()(val cc: ControllerComponents)
                                extends AbstractController(cc) {

  def callScript(arg: String): Unit = {
    println("Starting python script " + Thread.currentThread())
    val result = s"""python printArg.py ${arg}""" !!

    println(result)
    var x = true

    while (x) {
      Thread.sleep(1000)
      if (result.contains("Done")) {
        x = false
        println("Wow it worked")
      }
    }



  }
}
