package AdvancedScala2.Part2AdvancedFunctionalProgramming.exercises


trait ExerciseMySet[A] extends (A => Boolean) {

  def contains(elem: A): Boolean
  def +(elem: A): ExerciseMySet[A]
  def ++(anotherSet: ExerciseMySet[A]): ExerciseMySet[A]
  def map[B](f: A => B): ExerciseMySet[B]
  def flatMap[B](f: A => ExerciseMySet[B]): ExerciseMySet[B]
  def filter(predicate: A => Boolean): ExerciseMySet[A]
  def foreach(f: A => Unit): Unit

  def apply(elem: A): Boolean = {
    contains(elem)
  }

  def remove(elem: A): ExerciseMySet[A]
  def intersect(anotherSet: ExerciseMySet[A]): ExerciseMySet[A]
  def difference(anotherSet: ExerciseMySet[A]): ExerciseMySet[A]

  def unary_! : ExerciseMySet[A] //negation of a set
}

class ExerciseEmpty[A] extends ExerciseMySet[A] {

  /** Exercise: implement a functional set
   */
  override def contains(elem: A): Boolean = false

  override def +(elem: A): ExerciseMySet[A] = new ExerciseMySet[A] {}

  override def ++(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = ???

  override def map[B](f: A => B): ExerciseMySet[B] = ???

  override def flatMap[B](f: A => ExerciseMySet[B]): ExerciseMySet[B] = ???

  override def filter(predicate: A => Boolean): ExerciseMySet[A] = ???

  override def foreach(f: A => Unit): Unit = ???

  /** Exercise:
   * remove an element
   * intersection with another set
   * difference between two sets
   */
  override def remove(elem: A): ExerciseMySet[A] = ???

  override def intersect(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = ???

  override def difference(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = ???

  /** Exercise: Implement a unary_! == Negation of a set
   */
  override def unary_! : ExerciseMySet[A] = ???
}
