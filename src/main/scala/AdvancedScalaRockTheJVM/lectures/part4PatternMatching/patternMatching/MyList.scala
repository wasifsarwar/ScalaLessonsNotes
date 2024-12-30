package AdvancedScalaRockTheJVM.lectures.part4PatternMatching.patternMatching

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
case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
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
