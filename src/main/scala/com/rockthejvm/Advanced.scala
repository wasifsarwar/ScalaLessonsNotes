package com.rockthejvm

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object Advanced extends App {

  /**
    - lazy evaluation
    - lazy values are usedful in infinite collections
  */
  lazy val aLazyValue = 2
  lazy val lazyValueWithSideEffect = {
    println("I am so lazy")
    420
  }
  val eagerValue = lazyValueWithSideEffect + 1

  /**
   - "Pseudo-collections" : Option, Try
   - Option and Try are useful in unsafe methods, meaning methods that can return null
   - Options are collections which contains at most one element: if there's something it will be Some(), otherwise None
   */
  def methodThatCanReturnNull(): String = "hello, world"
  val anOption = Option(methodThatCanReturnNull)

  val stringProcessing = anOption match {
    case Some(string) => s"Found a string: $string"
    case None => "what the hell"
  }

  println(stringProcessing)

  /**
   - "Pseudo-collections" : Try
   - Try() returns a collection with either a value if the code went well, or an exception if the code threw one
   */

  def methodWhichCanThrowException(): String = throw new RuntimeException("runtime error bhai")

  try {
    methodWhichCanThrowException()
  } catch {
    case e : Exception => println("oopsie hehe")
    case _ => println("just do ya thang")
  }

  val aTry = Try(methodWhichCanThrowException())
  val anotherStringProcessing = aTry match {
    case Success(value) => s"hallelujah i got the right value here $value"
    case Failure(exception) => s"yikes we got an ${exception.getMessage}"
  }

  /**
    - Evaluate something on another thread
    - (asynchronous programming)
    - Future is a "collection" which contains a value when it is evaluated
    - Future[Unit]/whatever value is evaluated at the end of that future running
    - Future is composable with map, flatMap and filter
    - yes it is future
  */
  val aFuture = Future({
    println("Loading...")
    Thread.sleep(500)
    println("here's your value its 28")
    28
  })

  /**
    - The aFuture code above prints "Loading..." because the main thread of the application
    - finished before the future completed
    - adding thread.sleep(1000) for 10s allows future to complete before main jvm is done fully executing
   */
  Thread.sleep(1000)

  /**
    - Monads
    - Future, Try, Options are Monads
   */


  /**
    - Implicits basics
    - two common use cases
    - use case#1 : Implicit arguments
    - if there is method that accepts implicit arguments and there is a value with it,
      then it will use that without needing to call that value as parameters
  */

  def aMethodWithImplicitArgs(implicit arg: Int) = arg + 1
  implicit val myImplicitVal = 46
  println(aMethodWithImplicitArgs)

  /**
   - Implicits basics
   - use case#2 : Implicit conversions
   - By declaring the class as implicit,
     Scala automatically allows any integer to be wrapped by MyRichInteger whenever methods
     that are not natively available on Int are called.
   - This means that when you call 23.isEven(), Scala does not find an isEven method on the Int class directly.
     Instead, it searches for implicit conversions that can wrap Int to provide the isEven method.
     It finds the MyRichInteger class and uses it to wrap 23 into a MyRichInteger object, on which isEven is called.
   - Use implicits with care.
   */
  implicit class  MyRichInteger(n: Int) {
    def isEven() = n % 2 == 0
  }

  println(23.isEven())
  println(anotherStringProcessing)

}
