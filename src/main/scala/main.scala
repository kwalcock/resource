package org.clulab.resource

import java.io.FileNotFoundException
import java.nio.charset.StandardCharsets

import scala.io.BufferedSource
import scala.io.Source

object Main extends App {


  object Sourcer {
    val utf8: String = StandardCharsets.UTF_8.toString
    
    def sourceFromResource(path: String): BufferedSource = {
      val url = Option(Sourcer.getClass.getResource(path))
          .getOrElse(throw new FileNotFoundException(path))

      Source.fromURL(url, utf8)
    }
  }

  def getTextFromResource(path: String): String = {
    val source = Sourcer.sourceFromResource(path)
    val text = source.mkString
    
    source.close
    text
  }


  println(getTextFromResource("/resource.txt"))
}
