package RockTheJVMbasics

object PatternMatching extends App{

  // switch statement
  val anyInteger = 55
  val order = anyInteger match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => anyInteger+"th"
  }
  println(order)

  // PM is an expression
  case class Person(name:String, age:Int)
  val bob = Person("Bob", 43)

  // pattern matching can deconstruct case classes
  val personGreeting = bob match{
    case Person(s,a) => println(s"Hi I'm $s and I'm $a years old!")
    case _ => "Something else"
  }

  // it can also deconstruct tupules
  val aTuple = ("Bon Jovi", "Rock")
  val bandDescription = aTuple match {
    case (band, genre) => s"This $band belongs to $genre genre"
    case _ => "Not sure whats up"
  }

  // deconstruct lists
  val aList = List(1,2,3)
  val listDesconstructed = aList match {
    case List(_, 2, _) => "there's a 2 in the list"
    case _ => "not the list we want"
  }

  /*
   if pm doesn't match anything, it will throw MatchError
   PM will try all cases in sequence
   */
}
