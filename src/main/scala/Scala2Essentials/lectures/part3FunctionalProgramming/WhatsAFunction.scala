package Scala2Essentials.lectures.part3FunctionalProgramming

object WhatsAFunction extends App {

  /** DREAM: Use functions as first class elements
    * problem: JVM is built for OOP
    * pass functions as parameters
    * use functions as values
    * FIRST CLASS ELEMENTS: Objects(instances of classes)
    * Function traits, up to 22 params
    */

  val superFunction = new ActionFunctional[Int, Int] {
    override def apply(elem: Int): Int = elem * 2
  }

  //java class oop example
  trait ActionFunctional[A, B] {
    def apply(elem: A): B
  }

  println(superFunction(22))

  /** Function types = Function[A, B]
    * You can call upto 21 parameters -> Function1[A,B], Function2[A,B,C]....Function21[A,B,C,....,Q] where the last type is the return type
    * All SCALA FUNCTIONS are objects
    */

  val stringToIntConverter = new Function1[String, Int] {
    override def apply(v1: String): Int = v1.toInt
  }

  println(stringToIntConverter("3") + 4)

  val adder = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }

  val anotherAdder: (Int, Int) => Int = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }

  // final synthetic sugar
  val synAdder: (Int, Int) => Int = (v1: Int, v2: Int) => v1 + v2

  /** A function which takes 2 strings and concatenates them
    */
  val concat: (String, String) => String = (s1: String, s2: String) => s1 + s2
  println(concat("Wasif", " Siddique"))

  /** Define a function which takes an int and returns another function which takes an int and return an int
    * - what's the type of this function => Int
    * - how to do it
    */

  def returnFunc(n: Int) = {
    def anotherFunc(x: Int) = 32 + x
    anotherFunc(n)
  }

  /** Curried function
    * A curried function have the property they can be called multiple parameter
    * it receives some parameter, and returns a function that also takes parameters
    */

  val superAdder: Function1[Int, Function1[Int, Int]] =
    new Function1[Int, Function1[Int, Int]] {
      override def apply(x: Int): Function1[Int, Int] =
        new Function1[Int, Int] {
          override def apply(y: Int): Int = x + y
        }
    }

  // synthetic sugar
  def syntheticAdder: (Int) => ((Int) => Int) =
    (x: Int) => (y: Int) => x + y

  val adder2 = superAdder(3)
  println(adder2(4))
  println(superAdder(5)(6))

}
