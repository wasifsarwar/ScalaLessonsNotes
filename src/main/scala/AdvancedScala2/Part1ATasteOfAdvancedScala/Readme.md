# Scala Recap

## Instructions vs Expressions

---
- Instructions are the fundamental operation of imperative languages like Java, Python or C++
  - Instructions are executed in sequence to do things for programs
- In functional languages such as Scala, programs are constructed through expressions; by building expressions on 
  top of other expressions

## Code Block

---
```scala
val aCondition: Boolean = false
/**
 * The value of the following codeblock is always 56, since that's the last expression that is returned
 */
val aCodeBlock = {
  if (aCondition) 53
  56
}
``` 
- Compiler infers types for us. 
- `Unit` means the type of expressions that doesn't return a value, but does something when called. Compare to `void` in Java

## Functions, Recursion

---
```scala
import scala.annotation.tailrec
def aFunction(x: Int): Int = x + 1


def factorial(n: Int) : Int = {
  @tailrec
  def factorialAcc(n: Int, accumulator: Int): Int = {
    if (n <= 1) accumulator
    else factorialAcc(n - 1, n * accumulator)
  }
  factorialAcc(n, 1)
}
```
- Tail recursive functions allow us to not use additional stack frames for computing values

## Object-Oriented Language

---
```scala
class Animal
class Dog extends Animal
val aDog: Animal = new Dog //subtyping Polymorphism

trait Carnivore {
  def eat(a: Animal): Unit
}

class Crocodile extends Amimal with Carnivore {
  override def eat(a: Animal): Unit = println("My croc dinner tonight haha")
}

/**
  * method notations
 */
val aCroc = new Crocodile
aCroc.eat(aDog)
aCroc eat aDog

/**
 * All operators are method notations
 */
1 + 2
1.+(2)

/**
 * Anonymous Classes
 */
val aCarnivore = new Carnivore {
  override def eat(a: Animal): Unit = println("ROAAR")
}
```
Scala is more object-oriented than Java/C++ because it is designed around classes and objects


## Generics, Case Classes, Exceptions & Try/Catch/Finally

---
```scala
/**
 * generics and singletons/companions
 * @tparam A -> signifies covariance
 */
abstract class MyList[+A]
object MyList

/**
 * case classes
 */
case class Person(name: String, age: Int)

/**
 * exceptions and try/catch/finally
 */
val throwsException = throw new RuntimeException //Nothing
val aPotentialFailure = try {
  throw new RuntimeException
} catch {
  case e: RuntimeException => "I caught an exception"
} finally {
  println("somelogs")
}

```
- In `MyList[+A]`, `+A` signifies covariance
- `Nothing` has no instances. `Nothing` is also a proper replacement type for any other type
  - `Nothing` is the type of *nothingness* in Scala someway

## Functional Programming

---
In Scala, functions are instances of classes with `apply` methods
```scala
val incrementer = new Function1[Int, Int] {
  override def apply(v1: Int): Int = v1 + 1
}
incrementer(1)

val anonymousIncrementer = (x: Int) => x + 1 

/**
 * map, flatMap, filter, for-comprehension
 */
List(1,2,3).map(anonymousIncrementer) // Higher Order Function

val pairs = for {
  num <- List(1,2,3)
  char <- List('a', 'b', 'c')
} yield {
  num + "-" + char
}

/**
 * Collections: Options, Try
 * Pattern Matching
 */
val aOption = Some(2)
val x = 2 
val order = x match {
  case 1 => "first"
  case 2 => "second"
  case 3 => "three"
  case _ => x + "th"
}

val bob = Person("Bob", 22)
val greeting = bob match {
  case Person(n, _) => s"Hi, my name is $n"
}
```
