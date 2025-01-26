package AdvancedScala2.Part2AdvancedFunctionalProgramming

object PartialFunctions extends App {

  val aFunction =
    (x: Int) => x + 1 // Function[Int,Int] === Int param => Int return
  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 5) 413
    else if (x == 9) 12341
    else throw new FunctionNotApplicable

  class FunctionNotApplicable extends RuntimeException

  /** This is a partial function, because it's only for 1,5,9 in the Int domain
    * as it only accepts 1,5,9 as params
    */
  val aNicerFussyFunction = (x: Int) =>
    x match {
      case 1 => 42
      case 5 => 413
      case 9 => 12341
    }

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 5 => 413
    case 9 => 12341
  } // partial function value

  println(aPartialFunction(5)) // 413

  /** partial function utilities
    */
  println(
    aPartialFunction.isDefinedAt(5) // true
  ) // checks if a arg can be used in partial argument, returns boolean

  /** partial function lifts
    */
  val lifted =
    aPartialFunction.lift // Int => Option[Int], lifts to TotalFunction
  println(lifted(25)) // None
  println(lifted(9)) // Some(12341)

  /** partial function chains
    */
  val pfChain = aPartialFunction.orElse[Int, Int] { case 45 =>
    5532
  }
  println(pfChain(45)) // 5532
  println(pfChain.lift(453)) // None

  /** partial function extends normal functions
    */
  val aTotalFunction: Int => Int = { case 1 =>
    90
  }

  /** Higher Order Functions accept partial functions
    */
  val aMappedList = List(1, 2, 3).map {
    case 1 => 134
    case 2 => 34134
    // case 5 => 25324213 // this will crash
    case 3 => 25324213 // this will not crash
  }

  /** Note: Unlike functions that can have multiple parameters, partial functions can have only one parameter type
    */

  /** Exercise
    * 1. Construct a partial function instance by instantiating a anonymous class
    * -- instantiate an anonymous class that derives from a partial function
    */

  val myPartialFunction = new PartialFunction[Int, Int] {
    override def apply(v1: Int): Int = v1 match {
      case 1 => 134
      case 2 => 34134
      case 3 => 25324213
    }

    override def isDefinedAt(x: Int): Boolean = x == 1 || x == 2 || x == 3

  }

  /** 2. Dumb Chatbot as a partial function
    * -- enter prompt in console, chatbot will write back
    * -- write/read into console -> scala.io.Soruce.stdin.getLines().foreach(line => println(s"you said:+ $line")
    */
  val chatBot: PartialFunction[String, String] = {
    case "hello"    => "Hey, my name is ChokeGPT"
    case "goodbye"  => "bro where you going lmao"
    case "call mom" => "why not call mishti"
  }
  scala.io.Source.stdin
    .getLines()
    .map(chatBot)
    .foreach(println)
}
