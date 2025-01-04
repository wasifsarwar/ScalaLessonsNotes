package Scala2Essentials.lectures.part3FunctionalProgramming

object HOFsAndCurries extends App {

  /** Functional Programming = Working with functions
    * - pass functions as parameters
    * - return functions as results
    * Currying deals with functions with multiple parameter lists
    */

  /** SuperFunction is a higher order function e.g map, flatMap, filter
    * Return type of superFunction is another function that takes an Int and returns an Int
    * the second parameter of superFunction is another function (String, (Int => Boolean) that returns an Int
    * the second parameter of the second function parameter is another function that takes an Int and returns a Boolean Int => Boolean
    */

  //  val superFunction: (Int, (String, Int => Boolean) => Int) => (Int => Int) =
  //    ???

  //    val superFunction(n: Int, f: (String, Int => Boolean) => Int) => Int = {
  //    if (n <= 0 )
  //  }

  // function that applies a function n times over a value x
  // nTimes(f, n, x)
  // nTimes(f, 3, x) = f(f(f(x))) -> applies f over x 3 times

  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))
  }

  val plusOne = nTimes((x: Int) => x + 1, 3, 4)
  println(plusOne)

  // nTimesBetter(f, n) = x => f(f(f(...(x)))
  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) = {
    if (n <= 0) (x: Int) => f(x)
    else (x: Int) => nTimesBetter(f, n - 1)(f(x))
  }

  val betterPlusOne = nTimesBetter((x: Int) => x + 1, 4)
  println(betterPlusOne(12))

  /** Curried Functions
    */
  val superAdder: Int => (Int => Int) = (x: Int) => (y: Int) => x + y
  val add3 = superAdder(3)
  println(add3(10))
  println(superAdder(11)(13))

  /** Functions with multiple parameters list
    */

  // curriedFormatters converts x double to string
  // curriedFormatters act as a curried function, so you can later define sub functions that takes in a input and is passed to curriedFormatter
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  def standardFormat: (Double => String) = curriedFormatter("%4.2f")

  println(standardFormat(Math.PI))

  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) = { x => y =>
    f(x, y)
  }

  def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int = { (x, y) =>
    f(x)(y)
  }

  def composeInt(f: Int => Int, g: Int => Int): Int => Int = { x =>
    f(g(x))
  }

  def compose[A, B, T](f: A => B, g: T => A): T => B = { x =>
    f(g(x))
  }

  def andThenInt(f: Int => Int, g: Int => Int): Int => Int = { x =>
    g(f(x))
  }

  def andThen[A, B, T](f: A => B, g: B => T): A => T = { x =>
    g(f(x))
  }

}
