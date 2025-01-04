package Scala2Essentials.lectures.part3FunctionalProgramming.OptionsAndTry.exercises

import java.util.Random

object OptionsExercise extends App {
  /** Assume given API from some other programmers
   * Host and Port values are Optional
   * Connection Object apply returns Some(Connection) if random.nextBoolean(), otherwise returns failed connection (None)
   */

  val config: Map[String, String] = Map(
    // these values are fetched from somewhere else, we don't know if they'll
    // always have value
    "host" -> "176.45.36.1",
    "port" -> "80"
  )
  class Connection {
    def connect = "Connected" // connect to some server
  }

  object Connection {
    val random = new Random(System.nanoTime())
    def apply(host: String, port: String): Option[Connection] = {
      if (random.nextBoolean()) Some(new Connection)
      else None
    }
  }

  // try to establish a connection, if so - print the connect method

  /** We want to obtain a host and a port which might or not be there
   * given these, call apply which might or might not return a connection
   * if we get connection, then println(connect)
   */
  val host = config.get("host").orElse(None)
  val port = config.get("port").orElse(None)

  /** if (h != null)
   *  if (p != null)
   *    return Connection.apply(h,p)
   * else return null
   */
  val connection =
    host.flatMap(h => port.flatMap(p => Connection(host = h, port = p)))

  /** if (c != null)
   *  return c.connect
   * else return null
   */
  val connectionStatus = connection.map(c => c.connect)

  /** if (connectionStatus == null) println(None) else println(Some(connectionStatus.get))
   */
  println(connectionStatus)

  /** if (status != null)
   *  println(status)
   */
  connectionStatus.foreach(p => println(p))


  config.get("host")
    .flatMap(
      host => config.get("port").flatMap(
        port => Connection(host, port)
      )
    ).map(
      connection => connection.connect
    ).foreach(
      println
    )

  val forComprehensionConnection = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield {
    connection.connect
  }

  print("For Comprehension Connection: ")
  forComprehensionConnection.foreach(print)
}
