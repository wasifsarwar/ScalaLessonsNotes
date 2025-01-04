package Scala2Essentials.lectures.part3FunctionalProgramming.Collections

object TuplesAndMaps extends App {

  // tuples - finite ordered "lists"
  val aTuple = new Tuple2[Int, String](2, "hello, Scala")
  val anotherTuple = Tuple2(2, "hello, Scala")
  val moreTuple = (2, "haha silly", true)

  // Tuples can group upto 22 elements of different types, because they are used in conjunction with Function types

  println(anotherTuple._2)
  println(anotherTuple.copy(_2 = "bye java, hello scala"))
  println(anotherTuple.swap)
  println(moreTuple.toString())

  // Maps: keys -> values
  val aMap: Map[String, Int] = Map()
  val phoneBook =
    Map(("Jim", 55325), "Sam" -> 341235).withDefaultValue(
      -1
    ) // populate maps with tuples

  println(phoneBook)

  // Basic Map operations
  println(phoneBook.contains("Jim")) // takes a key, returns boolean
  println(phoneBook("Jim")) // .apply, returns Value associated with key

  /**   will throw a NoSuchElementException: Key not found: Silly if withDefaultValue not specified
    */
  println(phoneBook("Silly"))

  // add a pairing
  val newPairing = "June" -> 914724
  val newPhoneBook = phoneBook + newPairing
  println(newPhoneBook)

  // functionals on maps
  // map, filter, flatMap

  println(newPhoneBook.map(pair => pair._1.toLowerCase -> pair))
  println(newPhoneBook.map(pair => pair._1.toLowerCase -> pair._2 * 69))

  println(newPhoneBook.filter(_._1.startsWith("J")))

  //mapValues: updates values for all keys
  println(
    newPhoneBook.view.mapValues(num => "num multiplied: " + num * 10).toMap
  )

  // conversion to other collections
  println(phoneBook.toList)
  println(List(("Danerys", 2947)).toMap) // list of tuple, converts to map


  /**
   * GroupBy takes a predicate, and returns a HashMap where key matches predicate and value is tuple of matched elements
   */
  val names = List("Bob", "James", "Angela", "Mary", "Jim")
  println(names.groupBy(name => name.charAt(0))) // HashMap(J -> List(James, Jim), A -> List(Angela), M -> List(Mary), B -> List(Bob))
}
