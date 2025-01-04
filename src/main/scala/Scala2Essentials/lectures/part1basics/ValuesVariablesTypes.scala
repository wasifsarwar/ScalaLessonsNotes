package Scala2Essentials.lectures.part1basics

object ValuesVariablesTypes extends App {

  /**
   * a greeting function (name, age) => "Hi, my name is $name and I am $age years old
   */

  def greeting(name: String, age: Int): Unit = {
    println(s"Hi, my name is $name and I am $age years old")
  }

  /**
   * a factorial function 1 * 2 * 3 * ... * n
   * @param n is the input with type Long
   * @return the factorial of n
   */

  def factorial(n: Long): Long = {
    if (n == 1) n
    else n * factorial(n-1)
  }

  /**
   * A fibonacci function =>
   * f(1) = 1
   * f(2) = 1
   * f(3) = 3
   * f(n) = f(n-1) + f(n-2)
   * @param n is the input with type Int
   * @return the n'th fibonacci number
   */

  def fibonacci(n: Int) : Int =  {
    def calculate(o: Int) : Int = {
      if (o == 0) 0
      else if (o <= 2) 1
      else calculate (o - 1) + calculate (o - 2)
    }
    calculate(n)
  }

  /**
   *
   * @param n input number Int
   * @return whether input n is prime or not in boolean
   */

  def isPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean = {
      if (t <= 1) true
      else n % t == 0 && isPrimeUntil(t - 1)
    }
    isPrimeUntil(n/2)
  }
  greeting("Wasif", 27)
  println(factorial(15))
  println(fibonacci(0))
  println(isPrime(7))
}
