package RockTheJVMbasics

object FunctionalProgramming extends App {

  // Scala is OO
  class Person(name: String) {
    def apply(age: Int) = println(s"I have aged $age years")
  }

  val bob = new Person("Bob")
  bob(43) // invoking bob as a function == bob.apply(43)

  /*
    Scala runs on the jvm
    Functional Programming:
    - compose functions
    - pass functions as arguments
    - return functions as results

    Conclusion: FunctionX = Function1, Function2,...., Function22
   */

  val simpleIncrementor = new Function1[Int, Int] {
    override def apply(arg: Int): Int = arg + 1
  }

  simpleIncrementor(23)

  // all scala functions are instances of these FUNCTION_X types
  val stringConcatenator = new Function2[String, String, String] {
    override def apply(arg1: String, arg2: String): String = arg1 + arg2
  }

  stringConcatenator("I love ", "scala")

  // syntax sugars (alternative syntax to replace boilerplate code)
  val doubler: Int => Int = (x: Int) => 2 * x
  doubler(4) // 8

  val anotherDoubler = (x: Int) => 2 * x
  anotherDoubler(4)
  /*
    equivalent to the much longer

    val doubler = new Function1[Int, Int] {
      override def apply(x: Int): Int = 2 * x
    }
   */

  // higher-order functions: take functions as args/return functions as results
  // the map method is the higher order function
  val aMappedList = List(1, 2, 3).map(x => x + 1) // HOF
  println(aMappedList)

  val aFlatMappedList = List(1, 2, 3).flatMap { x =>
    List(2 * x)
  }
  println(aFlatMappedList)

  val aFilteredList = List(1, 2, 3, 4, 5).filter(x => x <= 3)
  val anotherFilteredList = List(1, 2, 3, 4, 5).filter(_ <= 3)

  // all pairs between 1,2,3 and the letters 'a', 'b', 'c'
  val allPairs = List(1, 2, 3).flatMap(number =>
    List('a', 'b', 'c').map(letter => s"$number-$letter")
  )
  println(allPairs)

  // for comprehensions
  val alternativePairs = for {
    number <- List(1, 2, 3)
    letter <- List('a', 'b', 'c')
  } yield s"$number-$letter"

// this is equivalent to the map/flatMap chain above
  println(alternativePairs)

  /*
   * Collections
   *
   */

  val aList = List(1, 2, 3, 4, 5)
  val firstElement = aList.head
  val rest = aList.tail
  val aPrependedList = 0 :: aList // returns List(0,1,2,3,4,5)
  val anExtendedList = 0 +: aList :+ 6 // returns List(0,1,2,3,4,5,6)

  // sequences
  val aSequence: Seq[Int] =
    Seq(1, 2, 3) // Seq.apply() seq is an abstract type, and
  val accessedElement = aSequence(1)

  // vectors: fast sequence implementation
  val aVector = Vector(1, 2, 3, 4, 5)

  // sets: collections with no duplicates
  val aSet = Set(1, 2, 3, 4, 5, 1, 2) //Set(1,2,3,4,5)
  val setHas6 = aSet.contains(6) //false
  val anAddedSet = aSet + 6 // Set(1,2,3,4,5,6)
  val aRemovedSet = aSet - 6 // Set(1,2,3,4,5)

  // ranges
  val aRange = 1 to 1000
  val twoBytwo = aRange.map(x => 2 * x).toList

  // tupes = groups of values under the same value
  val aTuple = ("Bon Jovi", "Rock", 1982)

  // maps
  val aMap: Map[String, Int] = Map(
    ("Daniel", 9292),
    "Jane" -> 12412
  )

}
