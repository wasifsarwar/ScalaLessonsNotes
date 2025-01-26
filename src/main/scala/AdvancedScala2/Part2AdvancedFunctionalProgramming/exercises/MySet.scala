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
  *  property based set: all the elements of type A that satisfy this predicate,  could be infinte number of these elements
  *  @param property it's a boolean function that takes an element of type A and returns a boolean
  */

class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {

  def contains(elem: A): Boolean = property(elem)

  /** This method adds an element to the set by creating a new ExercisePropertyBasedSet with an updated property function.
    * The new property function returns true if the element satisfies the original property or if it is equal to the element being added.
    * @param elem element type anything A to be added
    * @return new PropertyBasedSet with added element
    */

  // if (x in A | property(x)) + elem = { x in A | property(x) || x == elem }
  def +(elem: A): MySet[A] = new PropertyBasedSet[A](x =>
    /** This line creates a new instance of ExercisePropertyBasedSet with a new property function.
      * The new property function is a lambda expression x => property(x) || x == elem.
      * property(x) checks if the element x satisfies the original property.
      * x == elem checks if the element x is equal to the element being added.
      * The || operator ensures that the new property function returns true if either condition is met.
      */

    x == elem || property(x)
  )

  /** if (x in A | propery(x)) ++ anotherSet
    * = { x in A | property(x) || anotherSet }
    * = { x in A | property(x) || anotherSet contains x }
    */
  def ++(anotherSet: MySet[A]): MySet[A] =
    /** Here, a new instance of PropertyBasedSet is created with a property function
      * that checks if an element x satisfies either the original set's property or is contained in the anotherSet
      */

    /** This lambda expression returns true if the element x satisfies the original set's property (property(x))
      * or if x is contained in anotherSet (anotherSet.contains(x)
      */
    new PropertyBasedSet[A](x => property(x) || anotherSet.contains(x))

  /** mapping on a infinite set doesn't work, as we don't know if the
    * resulting set is infinite or not
    */
  def map[B](f: A => B): MySet[B] = politelyFail
  def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail

  /** here we are filtering the set based on the predicate and the property
    */
  def filter(predicate: A => Boolean): MySet[A] = new PropertyBasedSet[A](x =>
    property(x) && predicate(x)
  )
  def foreach(f: A => Unit): Unit = politelyFail

  /** if (x in A | property(x)) -- elem = { x in A | property(x) && x != elem }
    * if element is in the set, then we remove it by creating a new PropertyBasedSet
    * with a property function and a lambda expression that checks if an element x satisfies the original property and is not equal to the element being removed.
    */
  def remove(elem: A): MySet[A] = filter(x => x != elem)
  def intersect(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
  def difference(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)

  /** Negation of a set
    * { x in A | property(x) } => { x in A | !property(x) }
    * @return a new PropertyBasedSet with a property function satisfying condition x not in this set
    * that returns the elements not in this set
    */
  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail = throw new IllegalArgumentException(
    "Really deep rabbit hole!"
  )
}

class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {

  /** @param elem
    * @return true or false, based on whether the element is in the set
    * this function is looking at head == element and returns true, otherwise looks at remaining tail
    */
  def contains(elem: A): Boolean = {
    elem == head || tail.contains(elem)
  }

  /** @param elem
    * @return new Set with added element
    * this function first checks if element is already in set, if so then returns this
    * otherwise creates a new NonEmptySet with head as element and tail as this
    */
  def +(elem: A): MySet[A] = {
    if (this.contains(elem)) this //set doesn't allow duplicate values
    else new NonEmptySet[A](head = elem, tail = this)
  }

  /** @param anotherSet
    * @return new set added with this set
    * this function recursively calls tail.++(anotherSet) and adds head to the result
    */
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

  /** @param f function to map that takes A and returns a new element type B
    * @tparam B return type
    * @return a new Set with mapped elements
    * this function recursively calls tail.map(f) and adds head to the result
    */
  def map[B](f: A => B): MySet[B] = (tail map f) + f(head)

  /** @param f function to map that takes A and returns a new Set of element type B
    * @tparam B return type
    * @return a new Set with mapped elements
    * this function recursively calls tail.flatMap(f) and then ++ adds f(head) to the result
    */
  def flatMap[B](f: A => MySet[B]): MySet[B] = (tail flatMap f) ++ f(head)
  def filter(predicate: A => Boolean): MySet[A] = {
    val filteredTail = tail filter predicate
    if (predicate(head)) filteredTail + head
    else filteredTail
  }

  /** @param f a function that takes A and returns Unit
    *  in this function, first the function is applied to the head,
    *  and then tail.foreach(f) is recursively called
    */
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

  /** this is a simpler way to do intersect, where filter is called on anotherSet.contains() function
    */
  def basicIntersect(anotherSet: MySet[A]): MySet[A] =
    filter(x => anotherSet.contains(x))

  /** simpler intersect
    * since apply and contains are all and the same in MySet, we can call filter on anotherSet
    * intersect and filter are the same
    * @param anotherSet
    * @return intersect set
    */
  def intersect(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  /** Negation of a set
    *
    * @return a new MySet with a where elements not in this set
    * In this function, a new PropertyBasedSet is created with a property function that checks
    * if an element x is not in this set -> !this.contains(x)
    */
  def unary_! : MySet[A] = {
    new PropertyBasedSet[A](x => !this.contains(x))
  }

  /** @param anotherSet
    * @return a new Set with elements that are not in anotherSet
    * In this function, we filter elements in this set, where the element is not in anotherSet
    */
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
