package AdvancedScalaRockTheJVM.lectures.part3FunctionalProgramming.exercises

abstract class Maybe[+T] {

  def map[B](f: T => B): Maybe[B]
  def flatMap[B](f: T => Maybe[B]): Maybe[B]
  def filter(p: T => Boolean): Maybe[T]
}

case object MaybeNot extends Maybe[Nothing] {
  def filter(p: Nothing => Boolean): Maybe[Nothing] = MaybeNot
  def flatMap[B](f: Nothing => Maybe[B]): Maybe[B] = MaybeNot
  def map[B](f: Nothing => B): Maybe[B] = MaybeNot
}

case class Just[+T](value: T) extends Maybe[T] {

  /** For map, if I have a collection with one element, we return a new collection with that one element
    * For flatMap, since there's only a value in Just we return the evaluation of function(value)
    * For filter, if predicate returns True for this element T, we return 'this'. Otherwise, MaybeNot
    */

  def map[B](f: T => B): Maybe[B] = Just(f(value))
  def flatMap[B](f: T => Maybe[B]): Maybe[B] = f(value)
  def filter(p: T => Boolean): Maybe[T] = {
    if (p(value)) this
    else MaybeNot
  }
}

object TestMaybe extends App {

  val just3 = Just(3)
  println(just3)
  println(just3.map(x => x * 3))
  println(just3.flatMap(x => Just(2 + x)))
  println(just3.flatMap(x => Just(x % 3 == 1)))
  println(just3.filter(_ % 3 == 1))
}
