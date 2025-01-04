package Scala2Essentials.lectures.part3FunctionalProgramming.OptionsAndTry

import scala.util.{Failure, Success, Try}

object HandlingFailure extends App {

  // create success and failure explicitly

  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("Runtime failure lmao"))

  println(aSuccess)
  println(aFailure)

  def unsafeMethod(): String = throw new RuntimeException(
    "No string for you loser"
  )

  // Try objects via the apply method
  val potentialfailure = Try(unsafeMethod())
  println(potentialfailure)

  // syntax sugar
  val anotherPotentialFailure = Try {
    unsafeMethod()
  }

  // utilities
  println(potentialfailure.isSuccess)
  println(potentialfailure.isFailure)

  // OrElse
  def backUpMethod(): String = "Hey this backup method works"
  val fallBackTry = Try(unsafeMethod()).orElse(Try(backUpMethod()))
  println(fallBackTry)

  // If you design the API, this below is more readable
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
  def betterBackupMethod(): Try[String] = Success("A valid result")

  val betterFallback = betterUnsafeMethod() orElse betterBackupMethod()
  println(betterFallback)

  // Try type has map, flatMap, filter
  println(aSuccess.map(_ * 3))
  println(aSuccess.flatMap(x => Success(x * 10)))
  println(aSuccess.filter( _ > 15))
}
