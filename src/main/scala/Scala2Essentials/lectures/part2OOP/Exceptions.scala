package Scala2Essentials.lectures.part2OOP

/** Exceptions
  * exceptions are instances of classes
  * throwable classes extend the Throwable class
  * Exception and Error are the major Throwable subtypes
  * Exception denotes something is wrong with the program; NullPointerException
  * Error denotes something went wrong with the system, like StackOverflow error from never ending recursive calls, because the JVM runs out of memory
  * THROWING AN EXCEPTION RETURNS NOTHING
  */



object Exceptions extends App {

  val x: String = null
  // println(x.isEmpty) -> this will crash with a NullPointerException

  // throwing and catching exceptions
  //val aWeirdo = throw new NullPointerException

  // catching exceptions
  def getInt(withExceptions: Boolean): Int = {
    if (withExceptions) throw new RuntimeException("loser")
    else {
      43
    }
  }

  // potentialFail is AnyVal, the value returned from try is a Int perhaps, catch returns a Unit. That mismatch causes AnyVal
  val potentialFail =
    try {
      // code that might throw
      getInt(false)
    } catch {
      case e: RuntimeException => println("caught a runtimeException")
    } finally {
      // code that will get executed NO MATTER WHAT
      println("finally")
    }

  println(potentialFail)

  /** Defining a custom exception
    */

  class MyException extends Exception
  val myEx = new MyException

  class OverflowException extends Exception
  class UnderflowException extends Exception
  class MathCalculationException extends Exception

  /** Crash program with an OutOfMemoryError
    */

  def outOfMemory() = {
    val array = Array.ofDim(Int.MaxValue)
  }

//  outOfMemory()

  /** Crash with StackOverflowError
    */

  def stackOverflow(): Int = 1 + stackOverflow()

//  stackOverflow()

  /** PocketCalculator
    * - add(x,y)
    * - subtract(x,y)
    * - multiply(x, y)
    * - divide(x, y)
    *
    * Throw
    *  - OverflowException if add(x,y) exceeds Int.MAX_VALUE
    *  - UnderflowException if subtract(x,y) exceeds Int.MIN_VALUE
    *  - MathCalculationException if division by 0
    */

  object PocketCalculator {

    class OverflowException extends RuntimeException
    class UnderflowException extends RuntimeException
    class MathCalculationException
        extends RuntimeException("Division attempted by 0")

    def add(x: Int, y: Int): Int = {
      val result = x + y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result > 0) throw new UnderflowException
      else result
    }

    def subtract(x: Int, y: Int): Int = {
      val result = x - y
      if (x > 0 && y < 0 && result < 0) throw new OverflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      else result
    }

    def multiply(x: Int, y: Int) = {
      val result = x * y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result < 0) throw new OverflowException
      else if (x > 0 && y < 0 && result > 0) throw new UnderflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
    }

    def divide(x: Int, y: Int) = {
      if (y == 0) throw new MathCalculationException
      else x / y
    }
  }
}
