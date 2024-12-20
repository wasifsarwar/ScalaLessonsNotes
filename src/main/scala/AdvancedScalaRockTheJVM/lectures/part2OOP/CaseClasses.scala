package AdvancedScalaRockTheJVM.lectures.part2OOP

object CaseClasses extends App {

  /** Regular classes need equals, hashCode, toString implementation
    *  Case Classes has these ready
    */

  /** Case Class promotes parameters to fields, no need to add var
    */
  case class Person(name: String, age: Int)

  /** Sensible toString
    */
  val jim = new Person("Jim", 34)
  println(jim.name)
  println(jim.toString)
  println(jim)

  /** Equals and hashCode are implemented out of the box
    */
  val Jimbo = new Person("Jim", 34)
  println(jim == Jimbo) // returns true

  /** Case classes have handy copy method
    * copy method creates a new instance of case class.
    * It takes named parameters to create new class with modified data passed in
    */
  val jimmy = jim.copy(age = 33)

  /** Case classes have companion objecs
    */
  val thePerson = Person
  val mary = Person("Mary", 22)

  /** Case classes are serializable
    * AKKA framework sends serialized message via network
    */

  /** Case classes have extractor patterns = case classes can be used in pattern matching
    */

  /** there can be case object
    */

  case object UnitedKingdom {
    def name: String = "the UK sucks"
  }
}
