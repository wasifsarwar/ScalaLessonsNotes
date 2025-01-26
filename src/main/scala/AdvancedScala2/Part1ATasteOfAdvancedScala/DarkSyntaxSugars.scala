package AdvancedScala2.Part1ATasteOfAdvancedScala

import scala.util.Try

object DarkSyntaxSugars extends App {

  /** 1. Syntax sugar: Methods with single parameters
    */
  def singleArgMethod(arg: Int) = s"$arg little ducks"
  val description = singleArgMethod { // notice the curly braces
    3 + 3
  }
  println(description)

  val aTryInstance = Try { // reminiscent of Java's Try/Catch
    throw new RuntimeException
  }

  println {
    List(1, 2, 3).map { x =>
      x * 2
    }
  }

  /** 2. Syntax sugar: Single abstract method
    */
  trait Action {
    def act(x: Int): Int
  }
  val newAction = new Action {
    override def act(x: Int): Int = x + 1
  }
  val aFuncInstance: Action = x => x + 1

  // examples: Runables (Instances of traits/interfaces that can be passed on to threads)
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Hello, Scala!")
  })
  val aSweeterThread: Thread = new Thread(() => println("hola"))

  abstract class anAbstractType {
    def implemented: Int = 23
    def f(a: Int): Unit
  }

  val anAbstractInstance: anAbstractType = x => println(s"sweet jesus and $x")
  anAbstractInstance.f(3)

  /** 3. Syntax Sugar: the :: and #:: methods => which are special
    */

  val prependedList = 2 :: List(1, 3, 4)
  // 2.::(List(3,4)) -> this is absurd since there's no :: method for int
  //List(3,4).::(2)) -> compiler rewrites as this

  /** Scala Spec
    * Associativity of a method is defined by the last char of the method
    * if it ends in :, then it's right associative, otherwise it's left associative
    */

  val anotherWeird = 1 :: 2 :: 3 :: List(4, 5)
  val anotherWeirdEqualTo = List(4, 5).::(3).::(2).::(1)

  println(
    s"anotherWeird: $anotherWeird \nanotherWeirdEqualTo: $anotherWeirdEqualTo"
  )

  println(prependedList)

  val another = List(3, 2) +: List(1, 33, 4)
  val anotherEqualTo = List(1, 33, 4).+:(List(3, 2))
  println(another)
  println(anotherEqualTo)

  val h = 2 +: List(22)
  val i = 2 :: List(22)
  println(
    s"h: $h \ni: $i"
  )

  /** :: -> Adds an element at the beginning of this list.
    * +: -> Alias for prepended.
    * I think they do the same thing
    * : means right associative, as the right most param/thing will g
    */

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this //
  }

  /** --> is right associative as it ends in colon :
    * The :: and #:: methods are special
    */
  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  /** 4. Multiword method naming
    *     backticks `` allows you to have multple words in a method name
    */

  case class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"$name said $gossip")
  }

  val sornali = TeenGirl("Sornali")
  sornali `and then said` "hahah"

  /** 5. Generics Infix types
    */

  class Composition[A, B]
  val composite: Composition[Int, String] = ???
  val anotherComposite: Int Composition String = ???

  class -->[A, B]
  val towards: Int --> String = ???

  /** 6. update() is very special, much like apply()
    *    used in mutable collections
    *    Remember apply() and update()
    */

  val anArray = Array(1, 2, 3)
  anArray(2) =
    7 // rewritten to anArray.update(index, value) => anArray.update(2,7)

  /** 7. Setters for mutable containers
    *
    *  class Mutable {
    *    private var internalmember: Int = 0 // private for OO encapsulation
    *    def member = internalmember // getter
    *    def member_ = (value: Int) : Unit =
    *    internalmember = value // setter
    *  }
    *  val aMutableContainer= new Mutable
    *  aMutableContainer.member = 42
    *  print(aMutableContainer)
    */

}
