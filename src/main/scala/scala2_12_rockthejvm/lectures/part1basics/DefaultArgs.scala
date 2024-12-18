package scala2_12_rockthejvm.lectures.part1basics

object DefaultArgs extends App{
  def tailRecFactorial(n: Int, acc: Int): Int = {
    if (n <= 1) acc
    else tailRecFactorial(n-1, acc * n)

  }
}
