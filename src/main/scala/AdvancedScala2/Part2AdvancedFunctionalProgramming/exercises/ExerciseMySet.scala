package AdvancedScala2.Part2AdvancedFunctionalProgramming.exercises

import scala.annotation.tailrec

trait ExerciseMySet[A] extends (A => Boolean) {

  def contains(elem: A): Boolean

  def +(elem: A): ExerciseMySet[A]
  def ++(anotherSet: ExerciseMySet[A]): ExerciseMySet[A]
  def -(elem: A): ExerciseMySet[A]
  def --(anotherSet: ExerciseMySet[A]): ExerciseMySet[A]

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

  override def -(elem: A): ExerciseMySet[A] = this

  override def --(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = this

  override def map[B](f: A => B): ExerciseMySet[B] = new ExerciseEmpty[B]

  override def flatMap[B](f: A => ExerciseMySet[B]): ExerciseMySet[B] =
    new ExerciseEmpty[B]

  override def filter(predicate: A => Boolean): ExerciseMySet[A] = this

  override def foreach(f: A => Unit): Unit = ()

  override def intersect(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = this

  override def difference(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = this

  /** Since empty set contains no elements, the negation must mean including all elements negating against the property
    */
  override def unary_! : ExerciseMySet[A] = new ExercisePropertyBasedSet[A](_ =>
    true
  )
}

/** property based set: all the elements of type A that satisfy this predicate,  could be infinte number of these elements
  * @param property it's a boolean function that takes an element of type A and returns a boolean
  * @tparam A
  */

class ExercisePropertyBasedSet[A](property: A => Boolean)
    extends ExerciseMySet[A] {

  override def contains(elem: A): Boolean = property(elem)

  // the math is, return a new property set where element satisfies property
  override def +(elem: A): ExerciseMySet[A] =
    new ExercisePropertyBasedSet[A](x => property(x) || x == elem)

  override def ++(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] =
    new ExercisePropertyBasedSet[A](x => property(x) || anotherSet.contains(x))

  // here the math is, filter to get elements that do not math with passed in element to remove
  override def -(elem: A): ExerciseMySet[A] = filter(x => x != elem)

  override def --(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = filter(
    anotherSet
  )

  // For a partial function, the map method is not implemented
  override def map[B](f: A => B): ExerciseMySet[B] = properlyFail

  // For a partial function, the flatMap method is not implemented
  override def flatMap[B](f: A => ExerciseMySet[B]): ExerciseMySet[B] =
    properlyFail

  // the math is, if the property is true for the element, then the set is the same as the property and the predicate
  override def filter(predicate: A => Boolean): ExerciseMySet[A] =
    new ExercisePropertyBasedSet[A](x => property(x) && predicate(x))
  // For a partial function, the foreach method is not implemented
  override def foreach(f: A => Unit): Unit = properlyFail

  override def intersect(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] =
    filter(anotherSet)

  override def difference(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] =
    filter(x => !anotherSet(x))

  override def unary_! : ExerciseMySet[A] = new ExercisePropertyBasedSet[A](x =>
    !property(x)
  )

  def properlyFail = throw new RuntimeException(
    "This is a failure from exercise property based set"
  )
}

class ExerciseNonEmpty[A](head: A, tail: ExerciseMySet[A])
    extends ExerciseMySet[A] {

  override def contains(elem: A): Boolean = {
    if (head == elem) true
    else tail.contains(elem)
  }

  override def +(elem: A): ExerciseMySet[A] = {
    if (head == elem) this
    else new ExerciseNonEmpty[A](elem, this)
  }

  override def ++(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] =
    tail ++ anotherSet + head

  override def -(elem: A): ExerciseMySet[A] = {
    if (head == elem) tail
    else tail - elem + head
  }

  override def --(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] = filter(
    !anotherSet
  )

  override def map[B](f: A => B): ExerciseMySet[B] =
    tail.map(f) + f(head)

  override def flatMap[B](f: A => ExerciseMySet[B]): ExerciseMySet[B] =
    tail.flatMap(f) ++ f(head)

  override def filter(predicate: A => Boolean): ExerciseMySet[A] = {
    val filteredTail = tail filter predicate
    if (predicate(head)) filteredTail + head
    else filteredTail
  }

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail foreach f
  }

  override def intersect(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] =
    filter(anotherSet)

  override def difference(anotherSet: ExerciseMySet[A]): ExerciseMySet[A] =
    filter(!anotherSet)

  override def unary_! : ExerciseMySet[A] = new ExercisePropertyBasedSet[A](x =>
    !this.contains(x)
  )
}

object ExerciseMySet {
  def apply[A](values: A*): ExerciseMySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: ExerciseMySet[A]): ExerciseMySet[A] = {
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)
    }
    buildSet(values, new ExerciseEmpty[A])
  }
}

object exercisePlayground extends App {
  val set = ExerciseMySet(1, 2, 3, 4)
  set foreach println
  set + 5 foreach println
  set ++ ExerciseMySet(-1, -2) foreach println
  set + 3 foreach println

  println("testing new stuff")
  val newSet = ExerciseMySet(19, 220, 23121) ++ ExerciseMySet(43)
  newSet ++ ExerciseMySet(43) ++ set + 48 flatMap  (x =>
    ExerciseMySet(x, 12 + x)
  ) filter (_ % 2 == 0) foreach println

  val negative = !newSet // all the naturals not equal to 19, 220, 23121
  println(negative(19))
  println(negative(43))
  println(negative(6))

  val negativeEven = negative.filter(
    _ % 2 == 0
  ) // all even numbers > 0 not equal to 19, 220, 23121
  val negativeWith6 = negative + 6
  println(negativeWith6(6))
  println(negativeWith6(7))
  println(negativeWith6(19))
}
