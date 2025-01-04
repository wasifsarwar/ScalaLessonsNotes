package Scala2Essentials.lectures.part2OOP

object Objects extends App {

  /** Scala Objects:
    * are their own class
    * are the only instance
    * singleton pattern in one line!
    * Scala companions can access each other's private members
    * Scala is more OO than Java
    */

  // SCALA doesn't have class level functionality ("static")

  /** writing classes and objects with the same name is called companion, such as Person object and Person class below
    * This design with companions allows code to store something in instance level class, or a singleton object with class-level functionality
    *
    * Objects can have val, var or even methods
    * Objects can defined as Classes, but objects do not take any parameters
    * essentially is "static"/"class" - level functionality
    */

  object Person {
    // statjc/class level functionality
    val N_EYES = 2
    def canFly: Boolean = false

    // factory method
    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }

  class Person(name: String) {
    // instance level functionality
  }

  println(Person.N_EYES)
  println(Person.canFly)

  /** Scala objects are a SINGLETON instance -> when you define a object, you actually define it's type + Instance
    * Singleton means there's a single instance that can be referred to
    */
  val mary = Person // -> instance of Person
  val john = Person // -> instance of Person
  println(
    mary == john
  ) // true because both point to the same instance of Person object

  /**
    */

  val mariam = new Person("Mariam")
  val yusuf = new Person("Yusuf")

  // looks like a constructor, but is a apply method in the object
  val bobbie = Person(mariam, yusuf)

  /** Scala Applications = scala object with
    * def main(args: Array[String]): Unit -> needed for JVM which requires public static void main
    * extends App has def main
    */

}
