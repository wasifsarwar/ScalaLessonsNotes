package AdvancedScalaRockTheJVM.lectures.part2OOP

import scala.language.postfixOps

object MethodNotations extends App{

  class Person(val pName: String, val favoriteMovie: String) {
    def likes(movie: String): Boolean = {
      movie == favoriteMovie
    }
    // operator
    def +(person: Person): String = s"${this.pName} is hanging out with ${person.pName}"

    /*
     * Overload the + operator
     * mary + "the rockstar" => new person "Mary (the rockstar)"
     *
     */

    /*
     * Add an age to the Person class
     * Add a unirary + operator => new person with the age+1
     */

    /*
     * Add a "learns" method in the Person class => "Mary learns scala"
     * Add a learnScala method, calls learns method with "Scala"
     * Use it in postfix notation
     */

    /*
     * Overload the apply method
     * mary.apply(2) => "Mary watched Inception 2 times"
     */

    // unary operator
    def unary_! : String = s"what the heck!!"

    def isAlive: Boolean = true

    def apply(): String = s"Hi, my name is $pName and I like to watch $favoriteMovie"
  }

  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception") // Infix notation = operation notation. only for methods with one parameters
  val john = new Person("John", "Ice Age")

  /**
   * All Operators are Methods of parent objects
   * the following is an example of infix notation -> syntactic sugar
   */
  println(mary + john)

  /**
   * Prefix Notations
   * Unary operators are methods with unary underscore prexifed
   * unary_ prefix works with - + ~ !
   */
  val x = -1
  val y = 1.unary_-

  println(!mary)
  println(mary.unary_!)


  /**
   * Postfix notations
   * not really used, prefer to use .isAlive
   */

  println(mary isAlive)
  println(mary.isAlive)

  /**
   * apply
   * with apply you don't need to call it, just call class with parenthesis
   */
  println(mary.apply())
  println(mary())
}
