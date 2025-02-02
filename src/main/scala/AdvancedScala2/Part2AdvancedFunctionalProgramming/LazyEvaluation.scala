package AdvancedScala2.Part2AdvancedFunctionalProgramming

object LazyEvaluation extends App {

  /** lazy values are evaluated only once but only when used for the first time
    * Lazy delays the evaluation of values
    */
  lazy val x: Int = {
    println("hello")
    42
  }

  /** prints
    * hello
    * 42
    * 42
    */

  /** first run, x is evaluated and so println runs as part of evaluation
    * second run, x is stored in memory and isn't reevaluated and only returns 42 to be printed
    */
  println(x)
  println(x)

  /** Examples of implications:
    *  lazyCondition isn't evaluated until it is needed
    */
  def sideEffectCondition: Boolean = {
    println("Boo! ") // this isn't printed out
    true
  }

  def aCondition = false
  def anotherCondition = true
  lazy val lazyCondition = sideEffectCondition
  // since in runTime aCondition == false, lazyCondition doesn't get evaluated by compiler as it is not needed
  println(if (aCondition && lazyCondition) "yes" else "no")
  println(
    if (anotherCondition && lazyCondition) "yes" else "no"
  ) // prints Boo! yes

  /** in conjunction with call by Name
    */
  def byNameMethod(n: => Int): Int = n + n + n + 1
  def lazyTime(n: Int): Int = {
    val store = n
    store + 1
  }
  def retrieveMagicValue = {
    //side effect or long computation
    val time = lazyTime(0)
    println("waiting")
//    Thread.sleep(1000)
    println(s"its taking $time seconds")
    42
  }

//  println(byNameMethod(retrieveMagicValue))

  /** instead use lazy vals
    */

  def byNameMethodLazy(n: Int): Int = {
    lazy val t = n
    t + t + t + 1
  }

  val retreiveAnotherMagicValue = {
    //side effect or long computation
    val time = java.time.Instant.now()
    println("waiting")
//    Thread.sleep(1000)
    println(s"it took ${time.getNano * 1000} seconds")
    69
  }
  println(byNameMethodLazy(retrieveMagicValue))

  /** filtering
    */
  def lessThan30(n: Int): Boolean = {
    println(s"$n is less than 30?")
    n < 30
  }

  def greaterThan20(n: Int): Boolean = {
    println(s"$n is greater than 20?")
    n > 20
  }
  val numbers = List(1, 23, 32, 40, 5, 22)
  val lt30 = numbers.filter(lessThan30)
  println(lt30)

  println(lt30.filter(greaterThan20))

  /** withFilter uses lazy val under the hood
    * side effects and predicates are evaluated on a by-need basis
    */
  val lazyLt30 = numbers.withFilter(lessThan30)
  val gt20Lazy = lazyLt30.withFilter(greaterThan20)
  println
  println(gt20Lazy) // scala.collection.IterableOps$WithFilter@721e0f4f
  println
  println("testing withFilter")
  gt20Lazy.foreach(println)

  /** for-comprehensions use withFilter with guards
    * the following two are the same
    */

  for {
    a <- List(1, 2, 3) if a % 2 == 0
  } yield {
    a + 1
  }

  List(1, 2, 3).withFilter(_ % 2 == 0).map(_ + 1)

  /** Implement a lazily evaluated, single linked Stream of elements.
    * the head of stream is always available and evaluated
    * the tail is lazily evaluated
    */
  abstract class MyStream[+A] {
    def isEmpty: Boolean
    def head: A
    def Tail: MyStream[A]

    def #::[B >: A](element: B): MyStream[B] // prepend operator
    def ++[B >: A](stream: MyStream[B]): MyStream[B]

    def forEach(f: A => Unit): Unit
    def map[B](f: A => B): MyStream[B]
    def flatMap[B](f: A => MyStream[B]): MyStream[B]

    def filter(predicate: A => Boolean): MyStream[A]

    def take(n: Int): MyStream[A] // takes the first n elements of this stream
    def takeAsList(n: Int): List[A]
  }

  object MyStream {

    /** generate a stream based on a start element, and a generator function
      * generate the next value based on the previous value of the stream
      * @param start
      * @param generator
      * @tparam A
      * @return
      */

    /** naturals = MyStream.from(1)(x => x + 1) = stream of natural numbers, potentially infinite
      * naturals.take(100) // lazily evaluated stream of the first 100 natural (finite stream)
      * naturals.take(100).foreach(println) -> it'll be fine
      * naturals.foreach(println) -> stack overflow cause it's infinite
      * naturals.map(_ * 2) // stream of all even numbers (potentially infinite)
      */

//    def from[A](start: A)(generator: A => A): MyStream[A] = {
//
//    }
  }

}
