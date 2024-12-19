package AdvancedScalaRockTheJVM.lectures.part2OOP.exercises

/**
 * RECAP
 * Scala offers class-based inheritence with keyword 'extends'
 * access modifiers: private, protected, default(none = public)
 * need to pass in constructor argument to parent class
 * Derived classes cal override members or methods
 * Reuse parent fields/methods with super
 * Prevent inheritence with final and sealed
 */

abstract class MyList {

  /**
   * head = first element in the list
   * tail = remainder of the list
   * isEmpty = is this list empty
   * add(integer) => new list with this element added
   * toString => a string representation of the list
   */
  def head: Int
  def tail: MyList
  def isEmpty: Boolean
  def add(element: Int): MyList
  def printElements: String // this is a polymorphic call
  override def toString: String = "[" + printElements + "]"
}

// objects can extend classes
object Empty extends MyList {
  def head: Int = throw new NoSuchElementException
  def tail: MyList = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Int): MyList = new Cons(element, Empty)

  def printElements: String = ""
}

class Cons(h: Int, t: MyList) extends MyList {
  def head: Int = h
  def tail: MyList = t
  def isEmpty: Boolean = false
  def add(element: Int): MyList = new Cons(element, this)

  def printElements: String = {
    if (t.isEmpty) "" + h
    else h + ", " + t.printElements
  }
}

object ListTest extends App {
  val list = new Cons(1, Empty)
  println(list.head)
  val anotherList = new Cons(1, new Cons(2, new Cons(3, new Cons(4, Empty))))
  println(anotherList.tail.tail.tail.head)
  println(anotherList.add(5).toString)
}
