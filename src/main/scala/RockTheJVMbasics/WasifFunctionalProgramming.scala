package RockTheJVMbasics

object WasifFunctionalProgramming extends App{

  /**
    -  Scala is OO
   */
  class Person(name: String) {
    def apply(age: Int) : Unit = println(s"My name is $name and I have aged $age years")
  }

  val wasif = new Person("Wasif")
  wasif.apply(27)
  wasif(27)

  val simpleIncrementerWasif = new Function[Int, Int] {
    override def apply(arg: Int): Int = arg + 1
  }

  println(simpleIncrementerWasif(23))

  /**
    - all scala functions are instances of these functionX types
      FunctionX = Function1, Function2, Function3, ....., Function22
   */

  val stringConcatenatorWasif = new Function2[String, String, String] {
    override def apply(arg1: String, arg2: String) : String = arg1 + arg2
  }

  println(stringConcatenatorWasif("Wasif", "Siddique"))

  /**
  - Syntax Sugars: Alternative syntax that replaces boilerPlate code
  */

  val doubler: Function[Int, Int] = (x: Int) => 2 * x

  /**
    -
      new Function1[Int, Int]: Int {
        override def apply(arg: Int) : Int  2 * arg
      }
   **/

  val doublerSugar = (arg: Int)  => 2 * arg

  /**
    - methods or functions that takes functions as arguments are called higher order functions
    - takes functions as args, returns a functions or both
   */


  /**
    - map method on List type is higher order function
  */
  val aMappedList = List(1,2,3).map(x => x + 1)

  val flatMappedList = List(1,2,3).flatMap(x => List(x, 2 * x))
  val flatMappedList2 = List(1,5,3).flatMap { x =>
    List(2 * x)
  } // alternative syntax, same as .map(x => List(x, 2 * x))

  val aFlatMapList = List(1, 3, 44, 2, 51, 32, 63).flatMap(x=> List(x < 3))

  // Filter takes in an anonymous function that returns a boolean, and then returns a
  // list of elements that satisfy boolean conditions

  val aFilteredList = List(1, 3, 44, 2, 51, 32, 63).filter(x => x <= 46).sorted
  /**
    difference between map and flatMap
    1.
    Map has tranformation function that tranforms each element of a collection and returns a single new collection with transformed elements
    e.g: def map[B](f: A => B) : List[B]

    N.B the [B] after map indicates that B is a type parameter. This makes map return a list of elements of any type B

    FlatMap's transformation function returns a collection of transformed element for each element arg,
     and then returns a collection of all the collections that transformation function returns
    e.g: def flatMap[B](f: A => List[B]) : List[B]
   */



  // shorter syntax
  val filteredListShort = List(1, 3, 55, 23,12, 95).filter(_ <= 58) // notice removing x => with _ followed with boolean condition

  // all pairs between 1,2,3 and letters 'a', 'b', 'c'
  val numbers = List(1,2,3)
  val letters = List('a', 'b', 'c')

  val pairs = numbers.flatMap(number => letters.map(letter => s"$number-$letter")).sorted

  // for comprehension

  val anotherPairs = for {
    number <- numbers
    letter <- letters
  } yield {
    s"$number-$letter"
  } // equivalent to the map/flatMap chain above


  /**
   * Collections
  */

  // Lists
  val aList = List(1,2,3,4,5)
  val head = aList.head
  val tail = aList.tail
  val aPrependedList = 0 :: aList // :: refers to aPrepended list adds before head
  val aAppendedList = 0 +: aList :+ 6 //  +: adds at head, :+ adds at tail

  // Sequences
  val aSequence = Seq(1,2,3,4,5) // basically seq.apply() Seq is a abstract type

  val accessedElement = aSequence(2) // applying a sequence to an index will return that element in that index

  // vectors: fast Seq implementation
  val aVector = Vector(1,2,3,4,5)

  /**
    - sets = no duplicates.
    - set is fundamentally used for it's unique element properties, and contains is used to check
  */
  val aSet = Set(1,2,3,4,1,2) //only has unique elements, result should be Seq(1,2,3,4)
  val setHas5 = aSet.contains(5)
  val addedSet = aSet + 5 //
  val removedSet = aSet - 3
  val newSetHas5 = addedSet.contains(5)
  val newSatHas3 = removedSet.contains(3)


  /**
    - ranges
    - range.map returns IndexedSeq, so to grab list you need to use toList
  */
  val aRange = 1 to 1000
  val twoBytwo = aRange.map( x => 2 * x ).toList
  val twoBytwoEvens = aRange.filter(_ % 2 == 0).map( _ * 2).toList

  /**
    - tuples contain groups of values of different types under a same type
  */

   val aTuple = ("Bon Jovie", "Glam Rock", 1982) //tuple is defined with parentheses


  // maps
  val aMap : Map[String, Int] = Map(
    ("Wasif", 2),
    ("Sornali", 4),
    ("Labiba", 3)
  )

  val filterMap = aMap.view.filterKeys(_ == "Sornali").toList

  val mapMap = aMap.map {
    case (_, value) => value == 3
  }

  val findValue = aMap.find {
    case (_, value) => value == 3
  }

  println(aFlatMapList)
  println(aFilteredList)
  println(filteredListShort)
  println(accessedElement)
  println(pairs)
  println(aPrependedList)
  println(aAppendedList)
  println(anotherPairs)
  println(newSetHas5)
  println(newSatHas3)
  println(twoBytwo.length)
  println(twoBytwoEvens.length)
  println(filterMap)
  println(mapMap)
  println(findValue)


}
