package AdvancedScalaRockTheJVM.lectures.part2OOP

object ObjectOrientedBasics extends App{
  val Wasif = new Person("Wasif",27)
  Wasif("Khichuri")
}

/**
 *
 * @param name class constructor parameter, not a member part of the clss itself
 * @param age
 * class parameters are not fields
 * default parameters work in constructors as well
 */
class Persona(name: String, age: Int)

/**
 * to make class parameters fields, you need to add val to parameter inputs in constructor
 */
class Person(val name: String, val age: Int) {
  def apply(food: String) = {
    println(s"my name is $name, I am $age years old and I love eating $food")
  }

  /**
   * multiple constructors
   */
  def this(name: String) = this(name, 0)
  def this() = this("John Doe")
}

/**
 * method overloading is having same name method names with different
 * signatures, such as different inputs or return types
 */




