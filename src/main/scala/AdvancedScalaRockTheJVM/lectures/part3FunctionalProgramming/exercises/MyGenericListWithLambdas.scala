package AdvancedScalaRockTheJVM.lectures.part3FunctionalProgramming.exercises

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

/** - Does MyList support for comprehension?
  * map needs to take a function from A to B,  and returns a List[B] map(f: A=>B) => MyList[B]
  * filter needs to receive a predicate from A to Boolean, and returns a List[A] filter(p: A => Boolean) => MyList[A]
  * flatMap needs to receive a function from A to MyList[B] and returns a MyList[B] flatMap(f: A => MyList[B]) => MyList[B]
  */

abstract class MyList[+A] {

  /** head = first element in the list
    * tail = remainder of the list
    * isEmpty = is this list empty
    * add(integer) => new list with this element added
    * toString => a string representation of the list
    */
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  // B is a supertype of A, element is of type B, return type is MyCCGenericList[B] -> Because covariant
  // B >: A is saying add should accept anything that is B or subclass of B
  def add[B >: A](element: B): MyList[B]
  def printElements: String // this is a polymorphic call

  def map[B](transformer: A => B): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]
  def flatMap[B](
      transformer: A => MyList[B]
  ): MyList[B]

  def ++[B >: A](list: MyList[B]): MyList[B]

  /** forEach method A => Unit
    * [1, 2, 3].foreach(x => println(x))
    * @param f(x: A)
    * @tparam A
    */
  def forEach(f: A => Unit): Unit

  /** sort function ((A,A) => Int) => MyList
    * [1, 2, 3].sort((x,y) => y - x) => [3, 2, 1]
    * @param f
    * @tparam A
    * @return MyList
    */

  def sort(compare: (A, A) => Int): MyList[A]

  /** *
    *
    * @param list
    * @param zip
    * @tparam B
    * @tparam C
    * @return
    */
  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]

  /** fold(start)(function) => a value
    * [1, 2, 3].fold(0)(x + y) = 6
    * @return
    */
  def fold[B](start: B)(operator: (B, A) => B): B

  override def toString: String = "[" + printElements + "]"
}

// objects can extend classes
// nothing is a proper substitute for any type
// it should be nothing because it shouldn't hold any data or data type
case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] =
    new Cons(element, Empty)
  def printElements: String = ""

  def map[B](transformer: Nothing => B): MyList[B] =
    Empty
  def filter(predicate: Nothing => Boolean): MyList[Nothing] =
    Empty
  def flatMap[B](
      transformer: Nothing => MyList[B]
  ): MyList[B] = Empty

  def ++[B >: Nothing](list: MyList[B]) = list

  def forEach(f: Nothing => Unit): Unit = ()

  def sort(compare: (Nothing, Nothing) => Int) = Empty

  def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] = {
    if (!list.isEmpty)
      throw new RuntimeException("Lists do not have the same length")
    else Empty
  }

  def fold[B](start: B)(operator: (B, Nothing) => B): B = start
}

// since Cons is a subclass of covariant class MyGenericList, Con's class definition must specify[+A] as covariant
class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] =
    new Cons(element, t)
  def printElements: String = {
    if (t.isEmpty) "" + h
    else h + ", " + t.printElements
  }

  def filter(predicate: A => Boolean): MyList[A] = {
    if (predicate(head)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)
  }

  /*
  [1,2,3].map(n * 2) ==>
   = new Cons(2, [2,3].map(n * 2))
   = new Cons(2, new Cons(4, [3].map(n*2))
   = new Cons(2, new Cons(4, new Cons(6, Empty)))
   */

  def map[B](transformer: A => B): MyList[B] = {
    new Cons(transformer(h), t.map(transformer))
  }

  def ++[B >: A](list: MyList[B]): Cons[B] = {
    new Cons[B](h, t ++ list)
  }

  /*
    [1,2].flatMap(n => [n, n+1])
    = [1,1+1=2] ++ [2].flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
    = [1,2] ++ [2,3] ++ Empty
    = [1, 2, 2, 3]
   */

  def flatMap[B](
      transformer: A => MyList[B]
  ): MyList[B] = transformer(h) ++ t.flatMap(transformer)

  def forEach(f: A => Unit): Unit = {
    f(h)
    t.forEach(f)
  }

  def sort(compare: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] = {
      if (sortedList.isEmpty) new Cons(x, Empty)
      else if (compare(x, sortedList.head) <= 0) new Cons(x, sortedList)
      else new Cons(sortedList.head, insert(x, sortedList.tail))
    }
    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] = {

    if (list.isEmpty)
      throw new RuntimeException("Lists do not have same length")
    else new Cons(zip(h, list.head), t.zipWith(list.tail, zip))
  }

  /** @param start
    * @param operator
    * @tparam B
    * @return B
    * [1, 2, 3].fold(0)(+) =
    * = [2,3].fold(1)(+)
    * = [3].fold(3)(+)
    * = [].fold(6)(+)
    * = 6 which is the start value
    */

  def fold[B](start: B)(operator: (B, A) => B): B = {
    //val newStart = operator(start, head)
    // t.fold(newStart)(operator)
    t.fold(operator(start, head))(operator)
  }

}

object ListTestGenericWithCaseClass extends App {
  val list = new Cons(1, Empty)
  println(list.head)
  val anotherList: MyList[Int] = new Cons(
    1,
    new Cons(
      2,
      new Cons(
        3,
        new Cons(
          4,
          new Cons(
            5,
            Empty
          )
        )
      )
    )
  )
  val clonedList = new Cons(
    1,
    new Cons(
      2,
      new Cons(3, new Cons(4, Empty))
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

  // can't use _ multiple times
  println(
    list.map(
      _ * 2
    )
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
      new Cons(element, new Cons(element + 1, Empty))
    )
  )

  /** Testing forEach
    */
  anotherList.forEach(x => println(x))

  /** Testing sort
    */
  println(anotherList.sort((x, y) => y - x))
  val sortedList = anotherList.sort((x, y) => y - x)
  val listOfStrings: MyList[String] = new Cons(
    "Wasif",
    new Cons(
      "Labiba",
      new Cons(
        "Nusaiba",
        new Cons(
          "Sornali",
          new Cons(
            "Mishti",
            Empty
          )
        )
      )
    )
  )

  /** Testing zipWith
    */
  println(anotherList.zipWith[String, String](listOfStrings, _ + "-" + _))

  /** Testing fold
    */
  println(anotherList.fold(0)(_ + _))

  /** Testing for comprehension
    * for-comprehensions are expressions
    */
  val numString = for {
    number <- anotherList
    string <- listOfStrings
  } yield number + "" + string

  println(for {
    number <- anotherList
    string <- listOfStrings
  } yield number + "" + string)

  println(numString)
}
