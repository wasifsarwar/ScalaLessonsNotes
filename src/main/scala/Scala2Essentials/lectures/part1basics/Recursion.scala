package Scala2Essentials.lectures.part1basics

import scala.annotation.tailrec

object Recursion extends App {

  def factorial (n: BigInt): BigInt = {
    if (n <= 1) 1
    else{
      val result = n * factorial(n - 1)
      result
    }
  }

  println(factorial(950))

  def anotherFactorial(n: Int): Int = {
    @tailrec
    def factorialHelper(x: Int, acc: Int): Int = {
      if (x <= 1) acc
      else factorialHelper(x - 1, x * acc)
    }
    factorialHelper(n, 1)
  }
  println(anotherFactorial(10))

  /**
   *
   * @param s string to be concatenated
   * @param n number of times string is concatenated
   * @return concatenated string
   */

  def stringConcatenate(s: String, n: Int) : String = {
    @tailrec
    def StringConcatHelper(t: String, o: Int, acc: String): String = {
      if (o <= 0) acc
      else StringConcatHelper(t, o - 1, t + acc)
    }
    StringConcatHelper(s, n, "")
  }
  println(stringConcatenate("Wasif", 29))

  /**
   * @param n number to determine whether it is prime
   * @return boolean whether number is prime
   */
  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeHelper(t: Int, isStillPrime: Boolean): Boolean = {
      if (t <=1) true
      else if (!isStillPrime) false
      else
        isPrimeHelper(t - 1, n % t != 0 && isStillPrime)
    }
    isPrimeHelper(n/2, true)
  }
  println(isPrime(17))
  println(isPrime(2194))

  /**
   * @param n is the input number
   * @return the n'th fibonacci number
   * here n is just used to check whether the int o, which is the number
   * in the series of fibonacci that starts after 2 as it keeps getting added
   * isn't exceeding n itself
   */
  def fibonacci(n: BigInt): BigInt = {
    @tailrec
    def fibonacciHelper(o: BigInt, last: BigInt, nextToLast: BigInt): BigInt = {
      if (o >= n)  last
      else fibonacciHelper(o + 1, last + nextToLast, last)
    }
    if (n <= 2) 1
    else fibonacciHelper(2, 1, 1)
  }
}
