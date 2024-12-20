package AdvancedScalaRockTheJVM.lectures.part3FunctionalProgramming

/**
 * Instead of using anonymous FunctionX, we can use lambdas
 */

object AnonymousFunctions extends App {

  val doubler = new Function1[Int, Int] {
    override def apply(n: Int): Int = n * 2
  }

  /** Anonymous function (LAMBDA)
    * Lambda (x: Int) => x * 2 is a value of Function1[Int, Int]
    */
  val equivalentDoubler = (x: Int) => x * 2
  val anotherequivalent: Int => Int = x => x * 2

  /** multiple parameters
    */
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b
  val anotherAdder = (x: Int, y: Int) => x + y

  /** No parameters lambda
    */

  val noParam = () => 3
  println(noParam) // returns function instance
  println(noParam) // returns function call => function evaluated value

  /** Curly braces
    */
  val stringToInt = { (str: String) =>
    str.toInt
  }

  /** More synthetic sugar
    */
  val increment: Int => Int = _ + 1 // equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _ //equivalent (x, y) => x + y

  /** Rewrite the special adder as an anonymous function
    */
  val specialAdder = (x: Int, y: Int) => x + y

  // following is the lambda version of a curried function
  val superAdder = (x: Int) => (y: Int) => x + y
  println(superAdder(3)(5))

}
