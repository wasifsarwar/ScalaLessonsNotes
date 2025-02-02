package AdvancedScala2.Part2AdvancedFunctionalProgramming.exercises

import scala.annotation.tailrec

/** Implement a lazily evaluated, single linked Stream of elements.
  * the head of stream is always available and evaluated
  * the tail is lazily evaluated
  */
abstract class MyStream[+A] {
  def isEmpty: Boolean
  def head: A
  def tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B] // prepend operator
  def ++[B >: A](stream: => MyStream[B]): MyStream[B]

  def forEach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatMap[B](f: A => MyStream[B]): MyStream[B]

  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A] // takes the first n elements of this stream
  def takeAsList(n: Int): List[A] = take(n).toList()

  /** We added final because the subtypes of MyStream can override the toList in a non tailRec fashion
    * [1,2,3].toList([]) =
    * [2,3].toList([1]) =
    * [3].toList([2 1]) =
    * [].toList(2 1 3) = [1,2,3] //see note below about reversing list when
    */
  @tailrec
  final def toList[B >: A](acc: List[B] = Nil): List[B] = {

    /** because the element we are looking at is getting prepended, as in it is added in the front.
      * So we want to look at accumulator in reverse order every time
      */
    if (isEmpty) acc.reverse
    else tail.toList(head :: acc)
  }
}

object MyStream {

  /** generate a stream based on a start element, and a generator function
    * generate the next value based on the previous value of the stream
    * @param start
    * @param generator
    * @tparam A
    * @return
    */

  /** naturals = MyStream.from(1)(x => x + 1) = stream of natural numbers, potentially infinite
    * naturals.take(100) // lazily evaluated stream of the first 100 natural (finite stream)
    * naturals.take(100).foreach(println) -> it'll be fine
    * naturals.foreach(println) -> stack overflow cause it's infinite
    * naturals.map(_ * 2) // stream of all even numbers (potentially infinite)
    */

  def from[A](start: A)(generator: A => A): MyStream[A] = {
    new ConsStream(start, MyStream.from(generator(start))(generator))
  }
}

object EmptyStream extends MyStream[Nothing] {
  def isEmpty: Boolean = true

  def head: Nothing = throw new NoSuchElementException

  def tail: MyStream[Nothing] = throw new NoSuchElementException

  def #::[B >: Nothing](element: B): MyStream[B] =
    new ConsStream(hd = element, tl = this)

  def ++[B >: Nothing](stream: => MyStream[B]): MyStream[B] = stream

  def forEach(f: Nothing => Unit): Unit = ()

  def map[B](f: Nothing => B): MyStream[B] = this

  def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

  def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  def take(n: Int): MyStream[Nothing] = this

}

/** Combining a call by name parameter (=>) with a lazy val inside the implementation is called call-by need
  * @param head
  * @param tail
  * @tparam A
  */
class ConsStream[+A](hd: A, tl: => MyStream[A]) extends MyStream[A] {

  def isEmpty: Boolean = false

  override val head: A = hd

  override lazy val tail: MyStream[A] = tl

  /** @param element
    * @tparam B
    * @return
    * val s = new ConsStream(1, tail = EmptyStream) // EmptyStream is not evaluated here
    * val prepended = 1 #:: s = new Cons(1,s) // s will be lazily evaluated, whatever is in the tail of s is not evaluated
    */

  def #::[B >: A](element: B): MyStream[B] = new ConsStream(element, this)

  /** if called by name => then lazily evaluated
    * otherwise, eagerly on the spot evaluated
    */
  def ++[B >: A](stream: => MyStream[B]): MyStream[B] =
    new ConsStream(head, tail ++ stream)

  def forEach(f: A => Unit): Unit = {
    f(head)
    tail.forEach(f)
  }

  /** s = new ConsStream(1, ?)
    * mapped = s.map(_ + 1) = new Cons(2, s.tail.map( _ + 1))
    * ....s.tail.map doesn't get evaluated until mapped.tail is called
    */

  def map[B](f: A => B): MyStream[B] = new ConsStream(f(head), tail.map(f))

  def flatMap[B](f: A => MyStream[B]): MyStream[B] =
    f(head) ++ tail.flatMap(f) // preserves lazy evaluation

  def filter(predicate: A => Boolean): MyStream[A] = {
    if (predicate(head)) new ConsStream(head, tail.filter(predicate))
    else tail.filter(predicate)
  } // preserves lazy eval

  def take(n: Int): MyStream[A] = {
    if (n <= 0) EmptyStream
    else new ConsStream(head, tail.take(n - 1))
  }
}

object MyStreamPlayground extends App {
  val naturals = MyStream.from(1)(_ + 1)
  println(naturals.head)
  println(naturals.tail.head)
  println(naturals.tail.tail.head)
  println(naturals.take(100).forEach(println))
  val startfrom0 = 0 #:: naturals
  println(startfrom0.head)

  startfrom0.map(_ * 2).take(25).forEach(println)
  startfrom0
    .flatMap(x => new ConsStream(x, new ConsStream(x + 1, EmptyStream)))
    .take(25)
    .forEach(println)

  /** filter doesn't work here because infinite
    */
  // println(startfrom0.filter(_ < 10).toList()

  /** this will blow up too
    */
//  println(startfrom0.filter(_ < 10).take(10).toList())
  println(startfrom0.filter(_ < 10).take(9).take(20).toList())

  /** Exercises on streams
    * 1. stream of fibonacci numbers
    * [first,[ ... =
    * [first, fibo(second, second + first)
    */
  def fibonacciStream(first: BigInt, second: BigInt): MyStream[BigInt] = {
    new ConsStream(first, fibonacciStream(second, second + first))
  }

  println("fibonacci 100 below:")
  println(fibonacciStream(1, 1).takeAsList(100))

  /** 2. stream of prime numbers with Eratosthenes' sieve
    * Eraosthenes' sieve : [2 3 4 ...]
    * filter out all numbers by % 2 == 0
    * [3 ,5 ,7 , 9, 11]
    * filter out all numbers divisible by 3
    * [2, 3, 5, 7, 11, 13]
    * filter out all numbers divisible by 5
    */

  /** [2 3 4 5 6 7 8 9 10 ...]
    * the head of the number being processed has to be prime so the stream is
    * [2 3 5 7 9 13 15 ..]
    * [2 eratosthenes applied to numbers filtered by n % 2 != 0
    * [2 3 eratosthenes applied to [ 5 7 9 11...] filtered by n % 3 != 0
    */
  def eratosthenes(numbers: MyStream[Int]): MyStream[Int] = {
    if (numbers.isEmpty) numbers
    else
      new ConsStream(
        numbers.head,
        eratosthenes(numbers.tail.filter(n => n % numbers.head != 0))
      )
  }
  println
  println("eratosthenes: ")
  println(eratosthenes(MyStream.from(2)(_ + 1)).take(100).toList())
}
