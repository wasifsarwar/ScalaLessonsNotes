package AdvancedScalaRockTheJVM.lectures.part2OOP

object MethodNotationsExercises extends App {

  class Person(val pName: String, val age: Int, val favoriteMovie: String) {
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
    def +(title: String): Person = new Person(s"${this.pName} ($title)", {this.age}, this.favoriteMovie)

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

  val mary = new Person("Mary", 29, "A-Team")

  // Overload the + operator

  val maryNewName = mary + "the hoe"
  println(maryNewName.pName)
}
