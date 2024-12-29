# Collections

## Immutable Collections

***Immutable collections*** are found in `scala.collections.immutable` package

## Collection Hierarchy

![ImmutableCollectionsHierarchy.png](ImmutableCollectionsHierarchy.png)

### Scala offers both mutable and immutable collections:
- **Mutable** collections can be updated in place; **immutables** can never change 
- Scala standard library has type definitions/type aliases for **immutable** collections

### Immutable collections are found in scala.collections.immutable
- The head abstract class is ***Traversable***, which is extended by ***Iterable***
- ***Iterable*** is extended by ***Set***: ***HashSet*** & ***SortedSet***, ***Seq***, ***Map***: ***HashMap*** and ***SortedMap***
- ***Seq*** is a type of collection that can be traversed in a set order; e.g ***IndexedSeq*** and ***LinearSeq***

### ***IndexedSeq*** and ***LinearSeq***
- ***IndexSeq*** have properties that their elements can be accessed by index, hence *O(1)* search time
- ***LinearSeq*** only guarantee in some form of ordering of elements; e.g ***stacks*** and ***queues*** have *FIFO* or *LIFO*

## Mutable Collections

***Mutable collections*** are found in `scala.collections.mutable` package

## Mutable Hierarchy

![ImmutableCollectionsHierarchy.png](MutableCollectionsHierarchy.png)

## Traversable
Base trait for all collections. Offers a great variety of methods:
```
- maps       : map, flatMap, collect
- conversions: toArray, toList, toSeq
- size info  : isEmpty, size, nonEmpty
- tests      : exists, forall
- folds      : foldLeft, foldRight, reduceLeft, reduceRight
- retrieval  : head, find, tail
```

## Sequences
A *(very)* general interface for data structures that
- have a *well defined order*
- can be *indexed*

```scala
trait Seq[+A] {
  def head: A
  def tail: Seq[A]
}
```
Supports various operations:
- `apply`, `iterator`, `length`, `reverse` for indexing and iterating
- concatenation, appending, prepending
- grouping, sorting, zipping, searching, slicing

### List
A LinearSeq *immutable* linked list
- head, tail, `isEmpty` methods are fast: *O(1)*
- most other operations are *O(n)*: `length`, `reverse`
- keeps reference to tail

```scala
sealed abstract class List[+A]
case object Nil extends List[Nothing]
case object ::[A](val head: A, val tail: List[A]) extends List[A]
```

Lists are *Sealed*, it has two subtypes:
- `object Nil(empty)`
- `class ::`

### Array
The equivalent of simple Java arrays
- can be manually constructed with predefined lengths
- can be *mutated* (updated in place)
- are interoperable with Java's T[] arrays
- indexing is fast

```scala
final class Array[T]
  extends java.io.Serializable
  with java.lang.Cloneable
```

### Vector
The default implementation for *immutable sequences*
- *effectively constant* indexed read & write: *O(1)*
- fast element addition: append/prepend
- implemented as a fixed branch trie (branch factor 32)
- good performance for large sizes

```scala
val noElements = Vector.empty
val numbers = noElements :+ 1 :+ 2 :+ 3 //Vector(1, 2, 3)
val modified = numbers updated (0, 8) //Vector( 8, 2, 3)
```

```scala
final class Vector[+A]
```

### Lists vs Vectors
- List keeps references to tail vs Vector's depth of the tree is small
- Updating an element in List takes long time. Updating an element in vector means replacing an entire 32-element chunk

### Tuples

```scala
val tuple = (42, "RockTheJVM")
tuple._1  // 42  <----- retrieve elements using _n
tuple.copy(_1 = 0) // new tuple (0, "RockTheJVM") <----- create new tuples
tuple.toString // "(42, RockTheJVM)" <----- pretty print
tuple.swap // ("RockTheJVM", 42) <----- swap elements
```

### Maps
```scala
val phoneBook = Map("Jim" -> 4257, "Sally" -> 3184)
phoneBook.contails("Jim") //true
val anotherPhonebook = phoneBook + ("Swas" -> 91437)
```

Functions:
- `filterKeys`, `mapValues`
- `map`, `filter`, `flatMap` on pairings only
- `.toList` and `.toMap` for conversions
- `groupBy` 

