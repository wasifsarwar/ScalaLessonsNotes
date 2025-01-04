package Scala2Essentials.lectures.part4PatternMatching.patternMatching

import scala.util.Random

object PatternMatching extends App {

  // switch on steroids
  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 0 => "Z E R O"
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => s"$x is what we got :)"
  }

  println(x)
  println(description)

  /** Pattern Matching can decompose values
    * Cases are matched in order, first match will return corresponding expression
    * If there's no match, then MatchError, so always account for default or else cases
    * Type of pattern matching is the unification of all the expressions returned by all the cases
    * Pattern matching works really well with case classes, because they come with extractor patterns out of the box
    */

  case class Person(name: String, age: Int)
  val sornali = Person("Sornali", 24)

  val greeting = sornali match {
    case Person(n, a) => s"hi my name is $n and I am $a years old"
    case Person(n, a) if a > 25 =>
      s"hi my name is $n and I can get car rentals now!"
    case _ => "idk man"
  }
  println(greeting)

  /** Pattern matching on sealed hierarchies
    */

  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Cat(greeting: String) extends Animal

  val husky: Animal = Dog("husky")
  husky match {
    case Dog(someBreed) => println(s"Matched a dog of $someBreed breed")
  }

  // match everything ... don't do this
  val isEven = x match {
    case n if n % 2 == 0 => true
    case _               => false
  }
}
