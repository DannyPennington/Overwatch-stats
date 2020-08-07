package connectors

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import java.nio.file.{Paths, Files}
import scala.io.Source
import sys.process._

class PythonConnector @Inject()(val cc: ControllerComponents)
                                extends AbstractController(cc) {

  def callScript(arg: String = "1", arg2: String = "2"): Unit = {
    if (!Files.exists(Paths.get(s"public/images/1_$arg,2_$arg2.png"))) {
      println("Starting python script " + Thread.currentThread())
      val result = s"""python plotter.py ${arg} ${arg2}""" !!

      println(result)
      var x = true
      var count = 0
      while (x) {
        println(s"Current loop count is: $count")
        count += 1
        Thread.sleep(1000)
        if (result.contains("Done")) {
          x = false
          println("Script is finished")
        }
        else if (count > 20) {
          x = false
          println("Script timed out :(")
        }
        else if (Files.exists(Paths.get(s"public/images/1_$arg,2_$arg2.pdf"))) {
          x = false
          println("Image created")
        }
      }
    }
    else {
      println("Yo that file already exists so I'm not gonna do anything")
    }
  }

}
