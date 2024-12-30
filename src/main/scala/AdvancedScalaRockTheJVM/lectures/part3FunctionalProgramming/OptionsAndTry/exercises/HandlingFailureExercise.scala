package AdvancedScalaRockTheJVM.lectures.part3FunctionalProgramming.OptionsAndTry.exercises

import scala.util.{Random, Try}

object HandlingFailureExercise extends App {

  /** Try to obtain a connection from a server
    * Given the connection, try to obtain an HTML page from server
    */

  val host = "localhost"
  val port = "8080"
  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>Hello welcome to my page</html>"
      else throw new RuntimeException("Connection Interrupted")
    }

    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HTTPService {
    val random = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection = {
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port")
    }

    def getSafeConnection(host: String, port: String): Try[Connection] = Try(
      getConnection(host, port)
    )
  }

  /** If you get the HTML page from the connection, print it to the console i.e call renderHTML
    */

  val possibleConection = HTTPService.getSafeConnection(host, port)
  val possibleHTML =
    possibleConection.flatMap(connection => connection.getSafe("/home"))
  possibleHTML.foreach(renderHTML)

  //shorthand version
  HTTPService
    .getSafeConnection(host, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  for {
    connection <- HTTPService.getSafeConnection(host, port)
    html <- connection.getSafe("/home")
  } yield {
    renderHTML(html)
  }
}
