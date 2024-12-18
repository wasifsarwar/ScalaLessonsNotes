package com.rockthejvm

object ObjectOrientation extends App {

  //class and instance
  class Animal {
    val age: Int = 0
    def eat() = println("I'm eating")
  }
  val anAnimal = new Animal

  object Animal { // companions - companion object
    //companions can access each other's private fields/methods
    //singleton Animal and instances of Animal are different things
    // use Animal object to access private fields
    val canLiveIndefinitely = false
  }

  val animalLiveForever = Animal.canLiveIndefinitely // "static" fields/methods

  // inheritance
  class Dog(val name: String) extends Animal{

  }

  val aDog = new Dog("Lassie")
  //constructor arguments are not  fields


  // subtype polymorphism
  val aDeclaredAnimal: Animal = new Dog ("Hachi")
  aDeclaredAnimal.eat() //the most derived method (as in it'll call from Dog) is called in runtime. In compiled time, compiler only knows it comes from Animal, but in runtime the method from the most derived class will be executed

  // abstract class
  abstract class WalkingAnimal {
    val hasLegs = true //by default public, can restrict that by using private/protected
    def walk(): Unit
  }

  // "interface" = ultimate abstract type
  trait Carnivore {
    def eat(animal:Animal) : Unit
  }

  trait Philosopher {
    def ?!(thought: String): Unit //valid method name
  }

  // single-class inheritence, multi-trait "mixing" with
  //when implementing a method from a super type (instance) we call that an method override
  class Crocodile extends Animal with Carnivore with Philosopher {
    override def eat(animal:Animal) : Unit = println("I am a crocodile eating you, animal")

    override def ?!(thought: String): Unit = println(s"I was thinking: $thought")
  }

  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog // infix notation = object method argument, only available for methods with ONE argument
  aCroc ?! "what if we could fly"

  // operators in scala are actually methdos
  val basicMath = 1 + 2
  val anotherBasicMath = 1.+(2)

  // anonymous classes
  val dinosaur = new Carnivore {
    override def eat(animal: Animal): Unit = println("I am dinosaur I can eat anything RAWWR")
  }

  /*
    class Carnivore_Anonymous_1241 extends Carnivore {
      override def eat(animal: Animal): Unit = println("I am dinosaur I can eat anything RAWWR")
    }

    val dinosaur = new Carnivore_Anonymous_1241
   */

  // singleton object
  object MySingleton {
    private val mySpecialValue = 1344
    def mySpecialMethod() : Int = mySpecialValue
    def apply(x: Int): Int = x + 1
  }

  MySingleton.mySpecialMethod()
  MySingleton.apply(65)
  MySingleton(65) // equivalent to MySingleton.apply(65)


  /*
      Case classes = lightweight data structures with some boilerplate
      - sensible equals and hash code
      - serialization
      - companion with apply

      case classes can be constructed without new
   */

  case class Person(name:String, age:Int)
  val bob = Person("Bob", 54) // Person.apply("Bob", 54)

  // exceptions are special objects in jvm
  try {
    // code that can throw
    val x : String = null
    x.length
  } catch {
    case e : Exception => "some faulty error message"
  } finally {
    // execute some more code no matter what
  }

  // generics
  abstract class MyList[T] {
    def head: T
    def tail: MyList[T]
  }

  // using a generic with concrete type
  val aList: List[Int] = List(1,2,3) // List.apply(1,2,3)
  val first = aList.head // int
  val rest = aList.tail
  val aStringList = List("Hello", "Scala") // string
  val firstString = aStringList.head


  /*
   in scala , we operate with IMMUTABLE values/object
   any modification to an object should return a new object
   Benefits:
   1) works miracles in multithreaded environments
   2) Helps make sense of the code
   */
  val reversedList = aStringList.reverse // returns a new list

  /*
    Scala is closest to the object oriented ideal
    all the code
   */

}

