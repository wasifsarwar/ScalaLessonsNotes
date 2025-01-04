package Scala2Essentials.lectures.part3FunctionalProgramming

object MapFlatMapFilterFor extends App {

  val list = List(1, 2, 3)
  println(list.head)
  println(list.tail)
  println(list.tail.tail.head)

  //map
  println(list.map(_ * 2))
  println(list.map(_ + " is a number"))

  //filter
  println(list.filter(_ % 2 == 0))

  //flatMap
  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  // print out all combinations between two lists
  val numbers = List(1, 2, 3, 4)
  val char = List('a', 'b', 'c', 'd')
  val colors = List("black", "white")

  // print List('a1', 'a2', ..., 'd4')

  val comb = numbers.flatMap(n => char.map(c => "" + n + c))
  println(comb)

  /** If you have two loops, do a flatMap and a map
    * If you have three collections to loop through, do two flatMaps and then a map in the end
   * iterations below
    */

  val colorsComb = numbers.flatMap(n =>
    char.flatMap(c => colors.map(col => "" + n + c + "-" + col))
  )
  println(colorsComb)

  //for each
  list.foreach(println)

  // for-comprehension
  val forCombinations = for {
    n <- numbers if (n % 2 == 0)
    c <- char
    col <- colors
  } yield {
    "" + n + c + "-" + col
  }
  println(forCombinations)

  for {
    n <- numbers
  } println(n)

  // syntax overload
  list.map { x =>
    x * 2
  }

  /*
    1. MyList supports for comprehensions?
   */


  /*
    2. Implement a small collection of at most ONE element - Maybe[+T] - map, flatMap, filter
   */

}
