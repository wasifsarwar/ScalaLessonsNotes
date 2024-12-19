package AdvancedScalaRockTheJVM.lectures.part2OOP

import scala.language.postfixOps

/** For methods with one parameters, you can call without .()
  * this is infix notation
  *
  * For prefix notation you use unary operators, only allowed for +, - , ! , ~
  *
  * For postfix notation, only for methods with NO parameters
  */

object MethodNotationsExercises extends App {

  class Patron(val pName: String, val age: Int, val favoriteMovie: String) {
    def likes(movie: String): Boolean = {
      movie == favoriteMovie
    }

    // operator
    def +(person: Patron): String =
      s"${this.pName} is hanging out with ${person.pName}"

    /*
     * Overload the + operator
     * mary + "the rockstar" => new person "Mary (the rockstar)"
     *
     */
    def +(title: String): Patron =
      new Patron(s"${this.pName} ($title)", { this.age }, this.favoriteMovie)

    /** unary operator, used as prefix notations. Only used for + - ! ~
      */

    def unary_! : String = s"what the heck!!"

    /*
     * Add an age to the Person class
     * Add a unirary + operator => new person with the age+1
     */
    def unary_+ : Patron = new Patron(pName, age + 1, favoriteMovie)

    /** Postfix notations
      */
    def isAlive: Boolean = true

    /*
     * Add a "learns" method in the Person class => "Mary learns scala"
     * Add a learnScala method, calls learns method with "Scala"
     * Use it in postfix notation
     */

    def learns(topic: String): String = s"$pName learns $topic"

    def learnScala = this learns "scala"

    /** Apply method
      * @return
      */
    def apply(): String =
      s"Hi, my name is $pName and I like to watch $favoriteMovie"

    /*
     * Overload the apply method
     * mary.apply(2) => "Mary watched Inception 2 times"
     */
    def apply(num: Int): String = s"$pName watched $favoriteMovie $num times"

  }

  val mary = new Patron("Mary", 29, "A-Team")

  // Overload the + operator

  val maryNewName = mary + "the hoe"
  val maryNewAge = +mary
  println(maryNewName())
  println(mary())
  println(
    s"mary old age was ${mary.age}, but her new age is now ${maryNewAge.age}"
  )

  // add a learns method, use in postfix notation
  println(mary learnScala)

  // overloaded apply method
  println(mary(2))
}
