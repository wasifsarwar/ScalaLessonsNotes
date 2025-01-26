package AdvancedScala2.Part1ATasteOfAdvancedScala

object AdvancedPatternMatching extends App {

  /** We can define our own patterns by implementing a unapply in object
    * during pattern matching is looking for an unapply method defined in the same singleton object with same name as the pattern
    */

  /** Infix patterns :: or OR below
    * case x OR y
    * case head :: Nil equivalent to ::.(head, Nil)
    */

  /** Pattern matching allows us to decompose values that conform to a given pattern
    */

  val numbers = List(1)
  val description = numbers match {
    case head :: Nil =>
      println(s"the only element is $head") // :: infix pattern
    case _ =>
  }

  /** Structures available for pattern matching
    * - Constants
    * - Wildcards
    * - case classes
    * - tuples
    * - special magic like above
    */

  class Person(val name: String, val age: Int)
  object Person {
    def unapply(person: Person): Option[(String, Int)] = {
      if (person.age < 21)
        None
      else Some((person.name, person.age))
    }

    /** unapply can be method overloaded (different params)
      */
    def unapply(age: Int): Option[String] = {
      if (age < 22) Some("minor")
      else Some("major")
    }
  }
  val Wasif = new Person("Wasif", 27)
  val Nusaiba = new Person("Nusaiba", 19)
  val greeting = Nusaiba match {
    case Person(name, age) => println(s"Hey wassup, I'm $name and I'm $age")
    case _                 => println(s"loser")
  }

  /** the match is on Nusaiba.age because unapply only takes age : Int parameter
    * status is the return Option[String] from unapply
    */
  val anotherPattern = Nusaiba.age match {
    case Person(status) => println(s"My legal status is $status")
  }

  /** Exercise
    * Create my own pattern matching, match against multiple integer conditions
    * < 10 -> "a single digit"
    * % 2 == 0 -> "an even number"
    */
  object even {
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }

  object singleDigit {
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
  }

  val listOfNumbers = List(1, 2, 13, 14, 29, -199, -8)

  private val property = (number: Int) =>
    number match {
      case even()        => s"a single digit"
      case singleDigit() => s"an even number"
      case _             => "as no o"
    }

  println(listOfNumbers.map(property))

  /** Infix patterns
    */
  case class Or[A, B](a: A, b: B)
  private val either = Or(2, "Two")
  val humanDescription = either match {
    case Or(number, string) => s"$number is written as $string"
  }
  val betterDescription = either match {
    case number Or string =>
      s"cleaner code to generate that $number is written as $string"
  }
  println(humanDescription)
  println(betterDescription)

  /** Decomposing Sequences
    */
  val varang = numbers match {
    case List(1, _*) =>
      "starting with 1" //_* is a wildcard for multiple values in a list
  }

  abstract class MyList[+C] {
    def head: C = ???
    def tail: MyList[C] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+C](override val head: C, override val tail: MyList[C])
      extends MyList[C]

  object MyList {
    def unapplySeq[C](list: MyList[C]): Option[Seq[C]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }
  private val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  private val decompose = myList match {
    case MyList(1, 2, _*) => "starting with 1, 2"
    case _                => "something else"
  }
  println(decompose)

  /** return type of unapply is a custom return type like Option
    * The data structure we use needs two methods for pattern matching
    * - isEmpty: Boolean, get: Something
    */

  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false
      override def get: String = person.name
    }
  }

  println(Wasif match {
    case PersonWrapper(name) => s"this person's name is $name"
    case _                   => "an alien"
  })
}
