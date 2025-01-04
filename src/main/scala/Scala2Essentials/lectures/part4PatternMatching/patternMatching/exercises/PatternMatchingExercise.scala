package Scala2Essentials.lectures.part4PatternMatching.patternMatching.exercises

object PatternMatchingExercise extends App {

  trait Expression
  case class Number(n: Int) extends Expression
  case class Sum(e1: Expression, e2: Expression) extends Expression
  case class Product(e1: Expression, e2: Expression) extends Expression

  /** Write a simple function that uses pattern matching
    * It takes an expression, and returns a human readable form
    *
    * Sum(Number(2), Number(3)) => 2 + 3
    * Sum(Number(2), Number(3), Number(4)) => 2 + 3 + 4
    * Product(Sum(Number(2), Number(1)), Number(3)) = (2 + 1) * 3
    * Sum(Product(Number(2), Number(1)), Number(3)) = 2 * 1 + 3
    */

  def show(expr: Expression): String = {
    expr match {
      case Number(n)   => s"$n"
      case Sum(e1, e2) => show(e1) + " + " + show(e2)
      case Product(e1, e2) =>
        def maybeShowParentheses(express: Expression): String = express match {
          case Product(_, _) => show(express)
          case Number(_)     => show(express)
          case _             => "(" + show(express) + ")"
        }
        maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
    }

  }

  println(show(Sum(Number(2), Number(3))))
  println(show(Product(Sum(Number(2), Number(1)), Number(3))))
  println(show(Sum(Product(Number(2), Number(1)), Number(3))))
  println(show(Sum(Product(Number(2), Number(1)), Product(Sum(Number(3), Number(23)), Number(3)))))

}
