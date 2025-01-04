package Scala2Essentials.lectures.part4PatternMatching.patternMatching.exercises

object AllThePatternsExercise extends App {

  /**
   * Question: what would running the following code show in console?
   * "a list of strings" -? BUT WHY
   * This is JVM trick question.
   * This happens because JVM has to compatible with Java 1, which doesn't support generics
   */
  val numbers = List(1, 2, 3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfInts: List[Int] => "a list of integers"
    case _ => ""
  }
  println(numbersMatch) // should look like "a list of integers", but get "a list of strings"

  val correctNumbersMatch = numbers match {
    case listOfStrings: List[_] if listOfStrings.forall(_.isInstanceOf[String]) => "a list of strings"
    case listOfInts: List[_] if listOfInts.forall(_.isInstanceOf[Int]) => "a list of integers"
    case _ => ""
  }

  println(correctNumbersMatch)
}
