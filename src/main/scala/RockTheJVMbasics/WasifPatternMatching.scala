package RockTheJVMbasics

object WasifPatternMatching extends App {

  /** switch expression
    *   - pattern matching is an expression
    */
  val anInteger = 55
  val order = anInteger match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => anInteger + "th"
  }

  case class Person(name: String, age: Int)
  val bob = Person("Bob", 43)
  val rob = Person("Rob", 34)
  val people = (rob, bob)

  /**   - Here we are checking whether bob matches an object of case class Person
    */
  val personGreeting = people match {
    case (Person(name, age), _) =>
      s"Hi, my name is $name and I'm $age years old"
    case (Person(name, age), _) if name == "Rob" =>
      s"Hi, my name is $name and I'm $age years old"
    case _ => "Hey there!"
  }

  /**    - deconstructing tuple
    */

  val aTuple = ("Bob Joni", "Dock")
  val bandDescription = aTuple match {
    case (band, genre) => s"$band is of $genre genre"
    case _             => s"idk bro"
  }

  /**    - if pattern matching doesn't match anything it will throw a match error
    *    - pattern matching will try all cases in sequence
    */

  //decomposing lists
  val aList = List(1, 2, 3)
  val listDescription = aList match {
    case List(_, 2, _) => "List contains 2 on it's second position"
    case _             => s"couldn't find 2"
  }

  println(order)
  println(personGreeting)
  println(bandDescription)
  println(listDescription)
}
