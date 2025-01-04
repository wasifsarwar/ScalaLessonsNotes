package Scala2Essentials.lectures.part4PatternMatching.patternMatching

object AllThePatterns extends App {

  /** Constants
    */
  val x: Any = "Scala"
  val constants = x match {
    case 1              => " a number"
    case "Scala"        => "Found SCALA"
    case true           => "The Truth"
    case AllThePatterns => "A singleton object"
  }

  /** Match Anything
    * - wildcard
    */

  val matchAnything = x match {
    case _ => println
  }
  matchAnything

  /** Match Anything
    * - variables
    */
  val matchAVariable = x match {
    case something => s"I've found $something"
  }
  println(matchAVariable)

  /** Pattern matching on Tuples and nested Tuples
    */

  val aTuple = (1, 2)
  val matchATuple = aTuple match {
    case (1, 1)         => ""
    case (something, 2) => s"I've found $something"
  }
  println(matchATuple)

  val nestedTuple = (4, (5, 6))
  val matchNestedTuple = nestedTuple match {
    case (_, (5, v)) => s"found matched $v"
  }
  println(matchNestedTuple)

  /** Case classes - constructor pattern
    */

  val aList: MyList[Int] =  Cons(1, Cons(2, Empty))
  val matchAList = aList match {
    case Empty => "list is empty"
    case Cons(head, Cons(subhead, subtail)) => println(s"$head $subhead $subtail")
  }
  matchAList

  /**
   * List patterns
   */
  val aStandardList = List(1, 2, 3, 4, 52)
  val standardListMatching = aStandardList match {
    case List(1, _, _, _, _) => println // extractor
    case List(1, _*) => println // list of arbitrary length - advanced
    case 1 :: List(_) => println // infix pattern;
    case List(1,2,3) :+ 52 => println
  }

  /**
   * Type specifiers
   */
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case value: Int => println(s"this $value is an int") // explicit type specifier
    case _ => println("nahh")
  }

  /**
   * explicit name binding
   */
  val nameBindingMatch = aList match {
    case notEmpty @ Cons(_, _) => print(notEmpty) // explicit name binding => use the name later
    case Cons(1, rest @ Cons(2, _)) => print(rest) // explicit name binding in nested patterns
  }


  /**
   * Multi Patterns
   */
  val multipattern = aList match {
    case Empty | Cons(0, _) => print("found empty or 0") // compound pattern (multi-pattern)
  }

  /**
   * If guards
   */
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement % 2 == 0 => print("found a even")
  }
}
