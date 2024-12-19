package RockTheJVMbasics

import scala.annotation.tailrec

object Recursion extends App {

  def factorial(n: Int): Int = {
    if (n <= 1) 1
    else n * factorial(n - 1)
  }

  println(factorial(5000))

  def anotherFactorial(n: Int): BigInt = {
    def factHelper(n: BigInt, accumulator: BigInt): BigInt = {
      if (n <= 1) accumulator
      else factHelper(n - 1, n * accumulator)
    }
    factHelper(n, 1)
  }

  // concatenate a string n times with tail recursion
  def concatTailRec(str: String, n: Int): String = {
    @tailrec
    def concatHelper(n: Int, accumulator: String): String = {
      if (n <= 0) accumulator
      else concatHelper(n - 1, str + accumulator)
    }
    concatHelper(n, "")
  }

  println(concatTailRec("Sornali ", 5))

  //isPrime
  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeHelper(t: Int, bool: Boolean): Boolean = {
      if (!bool) false
      else if (t <= 1) true
      else isPrimeHelper(t - 1, n % t != 0 && bool)
    }
    isPrimeHelper(n / 2, true)
  }

  println(isPrime(13))
  println(isPrime(26))

  def fibonacci(n: Int): Int = {
    def fibonacciHelper(n: Int, acc: Int): Int = {
      if (n < 1) acc
      else fibonacciHelper(n - 1, n + acc)
    }
    fibonacciHelper(n, 0)
  }

  println(fibonacci(10))
  println(fibonacci(11))
  println(fibonacci(1))
  println(fibonacci(3))
  println(fibonacci(8))
  println(fibonacci(1))

}
