package Scala2Essentials.lectures.part2OOP.exercises

/** Case Classes Takeaways
  * Case classes are quick lightweight data structures with little boilerplate
  * Companions already implemented. Sensible equals, hascode, toString
  * Auto promotes parameters to fields
  * Case objects act like case classes
  */

/** RECAP
  * Scala offers class-based inheritence with keyword 'extends'
  * access modifiers: private, protected, default(none = public)
  * need to pass in constructor argument to parent class
  * Derived classes cal override members or methods
  * Reuse parent fields/methods with super
  * Prevent inheritence with final and sealed
  */

/** Generic trait MyPredicate[-T] with a little method test(T) => Boolean
  * trait MyCCPredicate[-T] {
  *      def test(element: T): Boolean
  *    }
  */

/** Generic trait MyTransformer[-A,B] with a method transformer(A) =>
  * trait MyCCTransformer[-A, B] {
  *    def transform(element: A): B
  *   }
  */

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

/** With map, flatMap and filter now returning a function as first class values
  * they are Higher Order Function
  * HOE either receives functions as parameters, or return other functions as result
  */

abstract class MyCCGenericList[+A] {

  /** head = first element in the list
    * tail = remainder of the list
    * isEmpty = is this list empty
    * add(integer) => new list with this element added
    * toString => a string representation of the list
    */
  def head: A
  def tail: MyCCGenericList[A]
  def isEmpty: Boolean
  // B is a supertype of A, element is of type B, return type is MyCCGenericList[B] -> Because covariant
  // B >: A is saying add should accept anything that is B or subclass of B
  def add[B >: A](element: B): MyCCGenericList[B]
  def printElements: String // this is a polymorphic call

  def map[B](transformer: A => B): MyCCGenericList[B]
  def filter(predicate: A => Boolean): MyCCGenericList[A]
  def flatMap[B](
      transformer: A => MyCCGenericList[B]
  ): MyCCGenericList[B]

  def ++[B >: A](list: MyCCGenericList[B]): MyCCGenericList[B]

  override def toString: String = "[" + printElements + "]"
}

// objects can extend classes
// nothing is a proper substitute for any type
// it should be nothing because it shouldn't hold any data or data type
case object EmptyCCGeneric extends MyCCGenericList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyCCGenericList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyCCGenericList[B] =
    new ConsCCGeneric(element, EmptyCCGeneric)
  def printElements: String = ""

  def map[B](transformer: Nothing => B): MyCCGenericList[B] =
    EmptyCCGeneric
  def filter(predicate: Nothing => Boolean): MyCCGenericList[Nothing] =
    EmptyCCGeneric
  def flatMap[B](
      transformer: Nothing => MyCCGenericList[B]
  ): MyCCGenericList[B] = EmptyCCGeneric

  def ++[B >: Nothing](list: MyCCGenericList[B]) = list
}

// since Cons is a subclass of covariant class MyGenericList, Con's class definition must specify[+A] as covariant
class ConsCCGeneric[+A](h: A, t: MyCCGenericList[A])
    extends MyCCGenericList[A] {
  def head: A = h
  def tail: MyCCGenericList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyCCGenericList[B] =
    new ConsCCGeneric(element, t)
  def printElements: String = {
    if (t.isEmpty) "" + h
    else h + ", " + t.printElements
  }

  def filter(predicate: A => Boolean): MyCCGenericList[A] = {
    if (predicate(head)) new ConsCCGeneric(h, t.filter(predicate))
    else t.filter(predicate)
  }

  /*
  [1,2,3].map(n * 2) ==>
   = new Cons(2, [2,3].map(n * 2))
   = new Cons(2, new Cons(4, [3].map(n*2))
   = new Cons(2, new Cons(4, new Cons(6, Empty)))
   */

  def map[B](transformer: A => B): MyCCGenericList[B] = {
    new ConsCCGeneric(transformer(h), t.map(transformer))
  }

  def ++[B >: A](list: MyCCGenericList[B]): ConsCCGeneric[B] = {
    new ConsCCGeneric[B](h, t ++ list)
  }

  /*
    [1,2].flatMap(n => [n, n+1])
    = [1,1+1=2] ++ [2].flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ Empty
    = [1, 2, 2, 3]
   */

  def flatMap[B](
      transformer: A => MyCCGenericList[B]
  ): MyCCGenericList[B] = transformer(h) ++ t.flatMap(transformer)

}

object ListTestGenericWithCaseClass extends App {
  val list = new ConsCCGeneric(1, EmptyCCGeneric)
  println(list.head)
  val anotherList = new ConsCCGeneric(
    1,
    new ConsCCGeneric(
      2,
      new ConsCCGeneric(3, new ConsCCGeneric(4, EmptyCCGeneric))
    )
  )
  val clonedList = new ConsCCGeneric(
    1,
    new ConsCCGeneric(
      2,
      new ConsCCGeneric(3, new ConsCCGeneric(4, EmptyCCGeneric))
    )
  )
  println(anotherList.tail.tail.tail.head)
  println(anotherList.add(5).toString)

  println(clonedList == anotherList)

  /*
    [1,2,3].map(n * 2) ==>
     = new Cons(2, [2,3].map(n * 2))
     = new Cons(2, new Cons(4, [3].map(n*2))
     = new Cons(2, new Cons(4, new Cons(6, Empty)))
   */

  println(
    anotherList
      .map((element: Int) => element * 2)
      .toString
  )

  /*
    [1,2,3].filter(n % 2 ==0) =>
    = [2,3].filter(n % 2 == 0)
    = new Cons(2, [3].filter(n % 2 == 0))
    = new Cons(2, Empty.filter(n % 2 ==0))
    = new Cons(2, Empty)
   */

  /** *
    *  println(anotherList.filter(new MyCCPredicate[Int] {
    *    override def filter(element: Int): Boolean = element % 2 == 0
    *  }))
    */

  println(anotherList.filter((element: Int) => element % 2 == 0))
  /*
  [1,2].flatMap(n => [n, n+1])
  = [1,1+1=2] ++ [2].flatMap(n => [n, n+1])
  = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
  = [1,2] ++ [2,3] ++ Empty
  = [1, 2, 2, 3]
   */

  println(
    anotherList.flatMap((element: Int) =>
      new ConsCCGeneric(element, new ConsCCGeneric(element + 1, EmptyCCGeneric))
    )
  )
}
