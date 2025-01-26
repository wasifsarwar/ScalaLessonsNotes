package AdvancedScala2.Part2AdvancedFunctionalProgramming.exercises

trait ExerciseMySet[A] extends (A => Boolean) {

  def contains(elem: A): Boolean

  def +(elem: A): ExerciseMySet[A]
  def ++(anotherSet: ExerciseMySet[A]): ExerciseMySet[A]
  def --(elem: A): ExerciseMySet[A]

  def map[B](f: A => B): ExerciseMySet[B]
  def flatMap[B](f: A => ExerciseMySet[B]): ExerciseMySet[B]
  def filter(predicate: A => Boolean): ExerciseMySet[A]
  def foreach(f: A => Unit): Unit

  def apply(elem: A): Boolean = {
    contains(elem)
  }

  def intersect(anotherSet: ExerciseMySet[A]): ExerciseMySet[A]
  def difference(anotherSet: ExerciseMySet[A]): ExerciseMySet[A]

  def unary_! : ExerciseMySet[A] //negation of a set
}

class ExerciseEmpty[A] extends ExerciseMySet[A] {

  /** Exercise: implement a functional set
    */
  override def contains(elem: A): Boolean = false

  override def +(elem: A): ExerciseMySet[A] =
    new ExerciseNonEmpty[A](elem, this)

  override def ++(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = anotherSet

  override def --(elem: A): ExerciseMySet[A] = this

  override def map[B](f: A => B): ExerciseMySet[B] = new ExerciseEmpty[B]

  override def flatMap[B](f: A => ExerciseMySet[B]): ExerciseMySet[B] =
    new ExerciseEmpty[B]

  override def filter(predicate: A => Boolean): ExerciseMySet[A] = this

  override def foreach(f: A => Unit): Unit = ()


  override def intersect(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = this

  override def difference(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = this

  /** Exercise: Implement a unary_! == Negation of a set
    */
  override def unary_! : ExerciseMySet[A] = ???
}

/** property based set: all the elements of type A that satisfy this predicate,  could be infinte number of these elements
  * @param property it's a boolean function that takes an element of type A and returns a boolean
  * @tparam A
  */

class ExercisePropertyBasedSet[A](property: A => Boolean)
    extends ExerciseMySet[A] {

  override def contains(elem: A): Boolean = property(elem)

  // the math is, return a new property set where element satisfies property
  override def +(elem: A): ExerciseMySet[A] = new ExercisePropertyBasedSet[A](x =>
    property(x) || x == elem
  )

  override def ++(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] =
    new ExercisePropertyBasedSet[A](x => property(x) || anotherSet.contains(x))

  override def --(elem: A): ExerciseMySet[A] = filter(x => x != elem)

  // For a partial function, the map method is not implemented
  override def map[B](f: A => B): ExerciseMySet[B] = properlyFail

  // For a partial function, the flatMap method is not implemented
  override def flatMap[B](f: A => ExerciseMySet[B]): ExerciseMySet[B] = properlyFail

  // the math is, if the property is true for the element, then the set is the same as the property and the predicate
  override def filter(predicate: A => Boolean): ExerciseMySet[A] = new ExercisePropertyBasedSet[A](
    x => property(x) && predicate(x)
  )
  // For a partial function, the foreach method is not implemented
  override def foreach(f: A => Unit): Unit = properlyFail

  // here the math is, filter to get elements that do not math with passed in element to remove
  override def --(elem: A): ExerciseMySet[A] = filter(x => x != elem)

  override def intersect(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = filter(anotherSet)

  override def difference(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = filter(x => !anotherSet(x))

  override def unary_! : ExerciseMySet[A] =

  def properlyFail = throw new RuntimeException("This is a failure from exercise property based set")
}

class ExerciseNonEmpty[A](head: A, tail: ExerciseMySet[A])
    extends ExerciseMySet[A] {

  override def contains(elem: A): Boolean = ???

  override def +(elem: A): ExerciseMySet[A] = ???

  override def ++(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = ???

  override def map[B](f: A => B): ExerciseMySet[B] = ???

  override def flatMap[B](f: A => ExerciseMySet[B]): ExerciseMySet[B] = ???

  override def filter(predicate: A => Boolean): ExerciseMySet[A] = ???

  override def foreach(f: A => Unit): Unit = ???

  override def remove(elem: A): ExerciseMySet[A] = ???

  override def intersect(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = ???

  override def difference(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = ???

  override def unary_! : ExerciseMySet[A] = ???
}
