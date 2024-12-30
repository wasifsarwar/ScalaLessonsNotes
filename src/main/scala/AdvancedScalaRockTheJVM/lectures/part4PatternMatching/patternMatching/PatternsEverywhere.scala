package AdvancedScalaRockTheJVM.lectures.part4PatternMatching.patternMatching

object PatternsEverywhere extends App {

  /** big idea #1
    * catches are actually MATCHES
    */

  try {
    //code
  } catch {
    case e: RuntimeException       => "runtime"
    case npe: NullPointerException => "nullpointerexception"
    case _                         => "Something else"
  }

  /** Big Idea #2
    * Generators are based on pattern matching
    * So are => Case Classes, :: Operators, ...
    */

  val list = List(1, 2, 3, 4)
  val evenOnes = for {
    x <- list if x % 2 == 0 // generator
  } yield x * 10

  val tuples = List((1, 2), (3, 4))
  val filterTuples = for {
    (first, second) <- tuples
  } yield {
    first * second
  }

  /** Big Idea #3
    * Multiple value definition based on pattern matching
    * Not limited to just tuples
    */
  val tuple = (1, 2, 3)
  val (a, b, c) = tuple // a is 1, b is 2, c is 3

  val head :: tail =
    list // :: deconstructs list into head and tail which is rest of the list
  println(head)
  println(tail)

  /** Big Idea #4
    * Partial Function
    */
  val mappedList = list.map {
    case v if v % 2 == 0 => s"$v is even"
    case 1               => "the one"
    case _               => "something else"
  } // partial function literal

  println(mappedList) // List(the one, 2 is even, something else, 4 is even)
}
