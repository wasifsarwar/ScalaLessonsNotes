package AdvancedScalaRockTheJVM.lectures.part2OOP.exercises

/** RECAP
  * Scala offers class-based inheritence with keyword 'extends'
  * access modifiers: private, protected, default(none = public)
  * need to pass in constructor argument to parent class
  * Derived classes cal override members or methods
  * Reuse parent fields/methods with super
  * Prevent inheritence with final and sealed
  */

/** Generic trait MyPredicate[-T] with a little method test(T) => Boolean
  */
trait MyPredicate[-T] {
  def test(element: T): Boolean
}

/** Generic trait MyTransformer[-A,B] with a method transformer(A) =>
  */
trait MyTransformer[-A, B] {
  def transform(element: A): B
}

/** MyGenericList:
  * - map(transformer) => MyList
  * - filter(predicate) => MyList
  * - flatMap(transformer from A to MyList[B]) => MyList[B]
  *
  * class EvenPredicate extends MyPredicate[Int]
  * class StringToIntTransformer extends MyTransformer[String, Int]
  *
  * [1,2,3].map(n*2) = [2,4,6]
  * [1,2,3,4].filter(n % 2) = [2,4]
  * [1,2,3].flatMap(n => [n, n+1]) => [1, 2, 2, 3, 3, 4]
  */

abstract class MyGenericList[+A] {

  /** head = first element in the list
    * tail = remainder of the list
    * isEmpty = is this list empty
    * add(integer) => new list with this element added
    * toString => a string representation of the list
    */
  def head: A
  def tail: MyGenericList[A]
  def isEmpty: Boolean
  // B is a supertype of A, element is of type B, return type is MyGenericList[B] -> Because covariant
  // B >: A is saying add should accept anything that is B or subclass of B
  def add[B >: A](element: B): MyGenericList[B]
  def printElements: String // this is a polymorphic call

  def map[B](transformer: MyTransformer[A, B]): MyGenericList[B]
  def filter(predicate: MyPredicate[A]): MyGenericList[A]
  def flatMap[B](
      transformer: MyTransformer[A, MyGenericList[B]]
  ): MyGenericList[B]

  def ++[B >: A](list: MyGenericList[B]): MyGenericList[B]

  override def toString: String = "[" + printElements + "]"
}

// objects can extend classes
// nothing is a proper substitute for any type
// it should be nothing because it shouldn't hold any data or data type
object EmptyGeneric extends MyGenericList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyGenericList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyGenericList[B] =
    new ConsGeneric(element, EmptyGeneric)
  def printElements: String = ""

  def map[B](transformer: MyTransformer[Nothing, B]): MyGenericList[B] =
    EmptyGeneric
  def filter(predicate: MyPredicate[Nothing]): MyGenericList[Nothing] =
    EmptyGeneric
  def flatMap[B](
      transformer: MyTransformer[Nothing, MyGenericList[B]]
  ): MyGenericList[B] = EmptyGeneric

  def ++[B >: Nothing](list: MyGenericList[B]) = list
}

// since Cons is a subclass of covariant class MyGenericList, Con's class definition must specify[+A] as covariant
class ConsGeneric[+A](h: A, t: MyGenericList[A]) extends MyGenericList[A] {
  def head: A = h
  def tail: MyGenericList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyGenericList[B] = new ConsGeneric(element, t)
  def printElements: String = {
    if (t.isEmpty) "" + h
    else h + ", " + t.printElements
  }

  def filter(predicate: MyPredicate[A]): MyGenericList[A] = {
    if (predicate.test(head)) new ConsGeneric(h, t.filter(predicate))
    else t.filter(predicate)
  }

  /*
  [1,2,3].map(n * 2) ==>
   = new Cons(2, [2,3].map(n * 2))
   = new Cons(2, new Cons(4, [3].map(n*2))
   = new Cons(2, new Cons(4, new Cons(6, Empty)))
   */

  def map[B](transformer: MyTransformer[A, B]): MyGenericList[B] = {
    new ConsGeneric(transformer.transform(h), t.map(transformer))
  }

  def ++[B >: A](list: MyGenericList[B]) = {
    new ConsGeneric[B](h, t ++ list)
  }

  /*
    [1,2].flatMap(n => [n, n+1])
    = [1,1+1=2] ++ [2].flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ Empty
    = [1, 2, 2, 3]
   */

  def flatMap[B](
      transformer: MyTransformer[A, MyGenericList[B]]
  ): MyGenericList[B] = transformer.transform(h) ++ t.flatMap(transformer)

}

object ListTestGeneric extends App {
  val list = new ConsGeneric(1, EmptyGeneric)
  println(list.head)
  val anotherList = new ConsGeneric(
    1,
    new ConsGeneric(2, new ConsGeneric(3, new ConsGeneric(4, EmptyGeneric)))
  )
  println(anotherList.tail.tail.tail.head)
  println(anotherList.add(5).toString)

  /*
    [1,2,3].map(n * 2) ==>
     = new Cons(2, [2,3].map(n * 2))
     = new Cons(2, new Cons(4, [3].map(n*2))
     = new Cons(2, new Cons(4, new Cons(6, Empty)))
   */
  println(
    anotherList
      .map(new MyTransformer[Int, Int] {
        override def transform(element: Int): Int = element * 2
      })
      .toString
  )

  /*
    [1,2,3].filter(n % 2 ==0) =>
    = [2,3].filter(n % 2 == 0)
    = new Cons(2, [3].filter(n % 2 == 0))
    = new Cons(2, Empty.filter(n % 2 ==0))
    = new Cons(2, Empty)
   */

  println(anotherList.filter(new MyPredicate[Int] {
    override def test(element: Int): Boolean = element % 2 == 0
  }))

  /*
  [1,2].flatMap(n => [n, n+1])
  = [1,1+1=2] ++ [2].flatMap(n => [n, n+1])
  = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
  = [1,2] ++ [2,3] ++ Empty
  = [1, 2, 2, 3]
 */

  println(anotherList.flatMap(new MyTransformer[Int, MyGenericList[Int]] {
    override def transform(element: Int): MyGenericList[Int] =
      new ConsGeneric(element, new ConsGeneric(element + 1, EmptyGeneric))
  }))
}
