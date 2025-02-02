package AdvancedScala2.Part2AdvancedFunctionalProgramming

/** Monads are a kind of types which have fundamental operations
  */
object Monads extends App {

  /** Our own Try Monad below.
    * Try has some case classes.
    * @tparam A
    */
  trait Attempt[+A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B]
  }
  object Attempt {
    def apply[A](aValue: => A): Attempt[A] = {
      try {
        Success(aValue)
      } catch {
        case e: Throwable => Fail(e)
      }
    }

  }
  case class Success[+A](value: A) extends Attempt[A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B] =
      try {
        f(value)
      } catch {
        case e: Throwable => Fail(e)
      }
  }
  case class Fail(e: Throwable) extends Attempt[Nothing] {
    def flatMap[B](f: Nothing => Attempt[B]): Attempt[B] = this
  }

  /** left identity
    * unit.flatMap(f) = f(x) // only for success case
    * Success(x).flatMap(f) = f(x) // proof
    */

  /** Right Identity
    * attempt.flatMap(unit) = attempt
    * if (attempt is successful) Success(x).flatMap(x => Attempt(x)) = Attempt(x) = Success(x)
    * Fail(_).flatMap(...) = Fail(e)
    */

  /** Associativity
    * attempt.flatMap(f).flatMap(g) == attempt.flatMap(x => f(x).flatMap(g))
    * Fail(e).flatMap(f).flatMap(g) == Fail(e)
    * Fail(e).flatMap(x => f(x).flatMap(g)) == Fail(e)
    * Success(value).flatMap(f).flatMap(g)) =
    *  f(value).flatMap(g) or Fail(e)
    *
    * Success(value).flatMap(x => f(x).flatMap(g)) =
    *  f(value).flatMap(g) or Fail(e)
    */

  val attempt = Attempt {
    throw new RuntimeException("My own monad, yes!")
  }
  println(attempt)

  /** Exercise:
    * Implement a lazy[T] monad = computation only executed when it's needed
    * implement unit/apply for companion object in lazy trait, and then flatMap
    */

  class MyLazyMonad[+A](value: => A) {
    //internal lazy val
    private lazy val internalValue = value
    def use: A = internalValue
    def flatMap[B](f: A => MyLazyMonad[B]): MyLazyMonad[B] = { f(internalValue) }
  }

  object MyLazyMonad {
    def apply[A](value: => A): MyLazyMonad[A] = new MyLazyMonad(value)
  }

  /** Monads = they have unit and flatMap
    * Monads = unit + map + flatten
    * given Monad[T] {
    *  def flatMap[B](f: T => Monad[B]): Monad[B] = ... implemented
    *
    *  def map[B](f: T => B):Monad[B] = ??
    *  def flatten(m: Monad[Monad[T]]): Monad[T] = ???
    */

  val lazyInstance = MyLazyMonad {
    println("Today I don't feel like doing anything")
    42
  }

  /**
   * running this will print == Today I don't feel like doing anything
   * because during flatMap f(value), value gets evaluated which contains print statement
   */
  println(lazyInstance.use)

  /**
   * since once evaluated, it doesn't run the print statement again
   */
  val flatMappedInstance = lazyInstance.flatMap(x => MyLazyMonad(x + 34))
  println(flatMappedInstance.use)

  val flatmappedInstance2 = flatMappedInstance.use

  /**
   * Left-Identity
   * unit.flatMap(f) = f(v)
   * Lazy(value).flatMap(f) = f(value)
   */

  /**
   * Right-Identity
   * lazy.flatMap(unit) = lazy
   * Lazy(value).flatMap(x => Lazy(x)) = Lazy(value)
   */

  /**
   * Associativity
   * lazy.flatMap(x => f(x).flatMap(g))
   * Lazy(value).flatMap(f).flatMap(g) = f(v).flatMap(g)
   * Lazy(value).flatMap(x => f(x).flatMap(g)) = f(value).flatMap(g)
   */

  /**
   * Map and Flatten in terms of flatMap
   * -
   * Monad[T] { // List
   *  def flatMap[B](f: T => Monad[B]): Monad[B] = ... (implemented)
   *
   *  def map[B](f: T => B): Monad[B] = flatMap(x => unit(f(x)) // Monad[B]
   *  def flatten(m: Monad[Monad[T]]): Monad[T] = m.flatMap((x: Monad[T]) => x)
   *
   *  List(1, 2, 3).map(_ * 2) = List(1,2,3).flatMap(x => List( x * 2))
   *  List(List(1,2), List(3,4)).flatten = List(List(1,2), List(3,4)).flatMap(x => x) = List(1,2,3,4)
   *
   */
}

