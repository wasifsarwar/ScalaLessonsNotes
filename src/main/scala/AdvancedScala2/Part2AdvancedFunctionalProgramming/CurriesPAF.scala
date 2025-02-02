package AdvancedScala2.Part2AdvancedFunctionalProgramming

object CurriesPAF extends App {

  /** partial function applications: Lifting methods to functions using _
    * Underscores are powerful. They help reduce function arity: convert functions from many parameters to few parameters
    */

  // curried functions
  val superAdder: Int => Int => Int = // Int => Int => Int === (Int, Int) => Int
    x => y => x + y

  val add3 = superAdder(3) // Int => Int == y => 3 + y
  println(add3(5)) // 8

  val anotherCurriedFunction: Int => Int => Int => Int = { x => y => z =>
    x - y * z
  }

  val add4 = anotherCurriedFunction(
    4
  ) // Int => Int => Int == y => z => 4 + y * z
  val subtract2 = add4(2) // Int => Int == z => 4 - 2 * z
  val multiply3 = subtract2(3) // 4 - 2 * 3

  println(multiply3) // 4 - 2 * 3 = -2

  // METHOD!
  def curriedAdder(x: Int)(y: Int): Int = x + y

  /** We need to add the type annotation to make it work. You can't curry a function without the type annotation
    * Here we converted a method into a function value
    * You can't use methods in HOF unless they are functions themselves.
    * Methods are part of instances of classes, not instances of functions
    * Tranforming a method into a function is called lifting.
    * FUNCTIONS != METHODS (JVM limitation)
    */

  val add6: Int => Int = curriedAdder(6)

  def inc(num: Int) = num + 1
  List(1, 2, 3).map(
    inc
  ) // List(2,3,4) // ETA-expansion, lifting inc method to a function value. Then map is used on the returned function value

  /** We do ETA expansion when we want partial function applications.
    * Here we are lifting the method inc to a function value.
    */
  val add7 = curriedAdder(7) _ // alternative syntax // Int => Int

  /** Exercises
    */
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddmethod(x: Int, y: Int): Int = x + y
  def curriedAddMethod(x: Int)(y: Int): Int = x + y

  /** add9 : Int => Int => y => 9 + y
    * Add as many different implementations of add 9 using the above
    */
  val simpleFunctionAdd9 = (x: Int) => simpleAddFunction(9, x)
  val simpleFunctionAdd9Curried = simpleAddFunction.curried(9)

  // partially applied function
  val add9PF = curriedAddMethod(9) _
  val add9FAlt = curriedAddMethod(9)(_) // alternative syntax

  /** Alternative syntax for turning methods into function values
    */
  val add9Method_altSyntaxAnother =
    simpleAddmethod(9, _: Int) // y => simpleAddMethod(9, y)
  val add9Function_altSyntaxAnother = simpleAddFunction(9, _: Int)

  /** Functions and methods can be applied with _.
    * _ are quite powerful.
    */
  def concatenator(a: String, b: String, c: String): String = a + b + c

  /** InsertName: x: String => Concatenator("Hello, this is", "Wasif", " how are you")
    */
  val insertName = concatenator(
    "Hello, this is ",
    _: String,
    " how are you"
  ) //another function
  println(insertName("Wasif"))

  val fillIntTheBlanks = concatenator("Hello, ", _: String, _: String)
  println(fillIntTheBlanks("Wasif", " Sucks!"))

  /** Exercise
    * 1.  Process a list of numbers and return their string representations with different formats. Use the %4.2f, %8.6f, %14.2f
    */

  def stringProcess(format: String)(num: Double): String = {
    format.format(num)
  }
  val nums = List(Math.PI, Math.E, 9.81, 1.3e-12)

  val simpleFormatMethod =
    stringProcess("%4.2f") _ // lifting the function with input %4.2f
  val secondFormat = nums.map(stringProcess("%8.6f")(_))
  val thirdFormat = nums.map(stringProcess("%14.2f")(_))

  println(nums.map(simpleFormatMethod))
  println(secondFormat)
  println(thirdFormat)

  /** Exercise
    * 2. Difference between
    *  - Functions vs Methods
    *  - Parameters by name vs lambdas
    *
    *  n :=> Int is a call by name parameter
    */

  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parentMethod(): Int = 42

  /** call byName and byFunction
    * - int, method, parentMethod, lambda, and a partially applied function
    * - Find out which one of these work, and which one doesn't
    */
  byName(23) // returns INT, ok
  byName(method) // OK
  byName(parentMethod()) // OK as well

  // first parentMethod is called to get the value, and on that value byName method is applied
  byName(parentMethod) // ok but beware ==> byName(parentMethod())

  // byName(() => 42) // not OK
  byName((() => 42)()) // OK
  // byName(parentMethod _) // not ok, since parentMethod is a function value it's not a substitute for byName parameter n

  // byFunction(45) // not ok

  /** methods without () doesn't go through ETA-expansion
    */
  // byFunction(method) // not ok!!! -> since method is evaluated to 42, it  doesn't work
  byFunction(parentMethod) // compiler does ETA expansion
  byFunction(() => 42) // works
  byFunction(parentMethod _) // works
  byFunction(method _) //lifting a method to a function
}
