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
