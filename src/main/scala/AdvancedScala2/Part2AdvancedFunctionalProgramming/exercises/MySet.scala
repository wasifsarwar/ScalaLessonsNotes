package AdvancedScala2.Part2AdvancedFunctionalProgramming.exercises

import scala.annotation.tailrec

/** @tparam A
  */
trait MySet[A] extends (A => Boolean) {

  /** Exercise: implement a functional set
    */
  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]
  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit

  def apply(elem: A): Boolean = {
    contains(elem)
  }

  /** Exercise:
    * remove an element
    * intersection with another set
    * difference between two sets
    */

  def remove(elem: A): MySet[A]
  def intersect(anotherSet: MySet[A]): MySet[A]
  def difference(anotherSet: MySet[A]): MySet[A]

  /** Exercise: Implement a unary_! == Negation of a set
    */
  def unary_! : MySet[A] //negation of a set
}

/** EmptySet is a class and not a singleton object because MyList[A] is invariant, not covariant
  * @tparam A
  */

class EmptySet[A] extends MySet[A] {
  def contains(elem: A) = false
  def +(elem: A): MySet[A] = new NonEmptySet[A](elem, this)
  def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  def map[B](f: A => B): MySet[B] = new EmptySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]
  def filter(predicate: A => Boolean): MySet[A] = this

  def foreach(f: A => Unit): Unit = ()
  def remove(elem: A): MySet[A] = this

  override def intersect(anotherSet: MySet[A]): MySet[A] = this
  override def difference(anotherSet: MySet[A]): MySet[A] = this

  def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true)
}

/** property based set: all the elements of type A that satisfy this predicate,  could be infinte number of these elements
  * { x in A | property(x) }
  */

class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {

  def contains(elem: A): Boolean = property(elem)

  // if (x in A | propery(x)) + elem = { x in A | property(x) || x == elem }
  def +(elem: A): MySet[A] = new PropertyBasedSet[A](x =>
    x == elem || property(x)
  )

  /** if (x in A | propery(x)) ++ anotherSet
    * = { x in A | property(x) || anotherSet }
    * = { x in A | property(x) || anotherSet contains x }
    */
  def ++(anotherSet: MySet[A]): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || anotherSet.contains(x))

  /** mapping on a infinite set doesn't work, as we don't know if the
    * resulting set is infinite or not
    */
  def map[B](f: A => B): MySet[B] = politelyFail
  def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail

  def filter(predicate: A => Boolean): MySet[A] = new PropertyBasedSet[A](x =>
    property(x) && predicate(x)
  )
  def foreach(f: A => Unit): Unit = politelyFail

  def remove(elem: A): MySet[A] = filter(x => x != elem)
  def intersect(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
  def difference(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)

  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail = throw new IllegalArgumentException(
    "Really deep rabbit hole!"
  )
}

class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {
  def contains(elem: A): Boolean =
    elem == head || tail.contains(elem)
  def +(elem: A): MySet[A] = {
    if (this.contains(elem)) this //set doesn't allow duplicate values
    else new NonEmptySet[A](head = elem, tail = this)
  }
  def ++(anotherSet: MySet[A]): MySet[A] = {
    tail ++ anotherSet + head //recursion + polymorphism

    /** Set(1,2,3) ++ Set(4,5)
      * -> Set(2,3) ++ Set(4,5) + 1
      * -> Set(3) ++ Set(4,5) + 1 + 2
      * -> Set() + Set(4,5) + 1 + 2 + 3
      * -> Set(4, 5) + 1 + 2 + 3
      * -> Set(4, 5, 1, 2, 3)
      */
  }

  def map[B](f: A => B): MySet[B] = (tail map f) + f(head)
  def flatMap[B](f: A => MySet[B]): MySet[B] = (tail flatMap f) ++ f(head)
  def filter(predicate: A => Boolean): MySet[A] = {
    val filteredTail = tail filter predicate
    if (predicate(head)) filteredTail + head
    else filteredTail
  }
  def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f) // or tail foreach f
  }

  /** @param elem
    * @return new Set with removed element
    * this function is looking at head == element and returns tail, otherwise looks at remaining
    * tail.head and adds the previous head to returned set
    */
  def remove(elem: A): MySet[A] = {
    if (head == elem) tail
    else tail.remove(elem) + head
  }
  //def intersect(anotherSet: MySet[A]): MySet[A] = filter(x => anotherSet.contains(x))

  /** simpler intersect
    * since apply and contains are all and the same in MySet, we can call filter on anotherSet
    * intersect and filter are the same
    * @param anotherSet
    * @return intersect set
    */
  def intersect(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  def unary_! : MySet[A] = {
    new PropertyBasedSet[A](x => !this.contains(x))
  }

  def difference(anotherSet: MySet[A]): MySet[A] =
    filter(x => !anotherSet.contains(x))
}

object MySet {
  // A* means multiple values of type A can be passed
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] = {
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)
    }
    buildSet(values, new EmptySet[A])
  }

  /** val set = MySet(1,2,3)
    * --> buildSet(seq(1,2,3), [])
    * = buildSet(seq(2,3), [] + 1)
    * = buildSet(seq(3), [1] + 2)
    * = buildSet(seq(), [1 , 2] + 3)
    * = [1, 2, 3]
    */
}

object playground extends App {
  val set = MySet(1, 2, 3, 4)
  set foreach println
  set + 5 foreach println
  set ++ MySet(-1, -2) foreach println
  set + 3 foreach println

  println("testing new stuff")
  val newSet = MySet(19, 220, 23121) ++ MySet(43)
  newSet ++ MySet(43) ++ set + 48 flatMap (x =>
    MySet(x, 12 + x)
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

/** Recap -> map, flatMap and filter doesn't work with property based sets
  * -> property based sets are infinite
  * -> property based sets are not iterable
  * -> property based sets are not mappable
  * -> property based sets are not flatmappable
  * -> property based sets are not filterable
  * -> property based sets are not foreachable
  */
