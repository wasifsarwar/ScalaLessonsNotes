package Scala2Essentials.lectures.part3FunctionalProgramming.OptionsAndTry

object Options extends App {

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  // unsafe API
  def unsafeMethod(): String = null
//  val result = Some(unsafeMethod()) // wrong
  val result = Option(unsafeMethod())
  println(result)

  // chained methods
  def backupMethod(): String = "A valid result"
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))
  println(chainedResult)

  // DESIGN unsafe APIS
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("Hooray some result")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()
  println(betterChainedResult)

  // Options Functions
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) //unsafe - Do NOT USE

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(_ > 10))
  println(myFirstOption.flatMap(x => Option(x * 10)))

  for {
    num <- myFirstOption
    if num % 2 == 0
  } yield {
    println(num * 32)
  }
}
