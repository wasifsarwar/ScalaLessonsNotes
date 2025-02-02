Functional Seq
---
- Sequences are *callable* through an integer index
```scala
trait Seq[+A] extends PartialFunction[Int, A] {
  def apply(index: Int): A // -> "gives me the element at the index in sequence"
}

val numbers = List(1, 2, 3)
numbers(1) // 2
numbers.apply(1) // 2
numbers(2) // 3
numbers(5) // java.lang.IndexOutOfBoundsException
```
- Sequences are partially defined on the domain [0, ..., length-1]
- *`Seq` is a partial function from `Int` to `A`*
- *Sequences* are **partial functions**

Functional Map
---
- Maps are *callable* through their keys:
```scala
trait Map[A, +B] extends PartialFunction[A, B] {
  def apply(key: A): B // -> "gives me the value for the key in the map"
  def get(key: A): Option[B] // -> "gives me the value for the key in the map wrapped in an Option"
}

val phoneMappings = Map(2 -> "ABC", 3 -> "DEF", 4 -> "GHI")
phoneMappings(2) // "ABC"
phoneMappings(5) // java.util.NoSuchElementException
```

Partial Functions
---
- A function is a partial function if it's not defined for all possible inputs
- For instance, a map is defined only for the keys it contains, where the keys are a subdomain of the type

Monads
---
Monads are a kind of types which have some fundamental operations
```scala
trait MonadTemplate[A] {
  def unit(value: A): MonadTemplate[A] // also called pure or apply
  def flatMap[B](f: A => MonadTemplate[B]): MonadTemplate[B] // also called bind in other languages
}
```
List, Option, Try, Future, Stream, Set are all *monads*

Operations must satisfy the *monad* laws

- Left identity: If you build a basic Monad out of an element, and you flatMap with a function it should give you the function applied to that element
- Right Identity: If you have a monad instance and you flatMap it with the unit function, it should give you the same monad instance
- Associativity: If you have a monad instance, and you flatMap it with two functions in a sequence, then that should give you the same thing as flatMapping the monad 
  instance with a composite function that does `flatMap(g)` for every element 
```scala
unit(x).flatMap(f) == f(x) // left identity
aMonadInstance.flatMap(unit) == aMonadInstance // right identity
m.flatMap.flatMap(g) == m.flatmap(x => f(x).flatMap(g)) // associativity
```

Example of Monad Law: List
---
```scala
/**
 * Left Identity
 */
List(x).flatMap(f) = f(x) + Nil.flatMap(x)
= f(x)

/**
 * Right identity
 */
list.flatMap(x => List(x)) = list

/**
 * Associativity law
 */
[a, b, c].flatMap(f).flatMap(g) = 
  (f(a) + f(b) + f(c)).flatMap(g) =
    f(a).flatMap(g) ++ f(b).flatMap(g) ++ f(c).flatMap(g) = 
      [a, b, c].flatMap(f(_)).flatMap(g) =
        [a, b, c].flatMap(x => f(x).flatMap(g))
```

Another Example of Monad Law: Option
---
```scala
/**
 * Left Identity
 */
Option(x).flatMap(f) = f(x)
Some(x).flatMap(f) = f(x)

/**
 * Right Identity
 */
opt.flatMap(x => Option(x)) = opt
Some(value).flatMap(x => Option(x)) = Option(v) = Some(v)

/**
 * Associativity
 */
o.flatMap(f).flatMap(g) = o.flatMap(x => f(x).flatMap(g))
Some(v).flatMap(f).flatMap(g) = f(x).flatMap(g) = f(v).flatMap(g)
```
