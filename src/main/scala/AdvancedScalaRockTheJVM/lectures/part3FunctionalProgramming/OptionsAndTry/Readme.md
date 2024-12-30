# Options and Try

## Options
An *Option* is a wrapper for a value that might be present or not
- `Some` wraps a concrete value
- `None` is a singleton for absent values
- Options mean the possible absence of value

```scala
sealed abstract class Option[+A]
case class Some[+A](x: A) extends Option[A]
case object None extends Option[Nothing]
```

Options are present in many places
```scala
val map = Map("key" -> "value")
map.get("key") // Some(value)
map.get("None") // None

/***
 * map uses options on its basic get operation, prefer over apply
 * lots of functions all collections work with options
 */
  
val numbers = List(1, 2, 3)
numbers.headOption // Some(1)
numbers.find(_ % 2 == 0) // Some(2)
```

Use *Options* stay away from the Boogeyman:
- avoid runtime crashes due to NullPointerExceptions
- avoid an endless amount of null-related assertions

Options are a functional way of dealing with absence
- *map*, *filter*, *flatMap*
- *orElse*
- others: *fold*, *collect*, *toList*

If you design a method to return a (some type) but may return null, return an **Option[that Type]** instead.

## Handling Failures - Let's Try[T]
Exceptions are handled inside try-catch blocks:
```scala
import scala.sys.process.processInternal.IOException
try {
  val config: Map[String, String] = loadConfig(path)
} catch {
  case _: IOException => // Handle IOException
  case _: Exception => // Handle other exceptions  
}
```
- multiple/nested try's make the code hard to follow
- we can't chain multiple operation prone to failure

#### A Try is a wrapper for computation that might fail or not
```scala
sealed abstract class Try[+T]
case class Failure[+T](t: Throwable) extends Try[T] // wrap failed computations
case class Success[+T](value: T) extends Try[T] // wrap succeeded computations
```

Use Try to handle exceptions gracefully:
- avoid runtime crashes due to uncaught exceptions
- avoid an endless amount of try-catches

A functioal way of dealing with failure
- *map*, *flatMap*, *filter*
- *orElse*
- others: *fold*, *collect*, *toList*, conversion to *Option[]*

If you design a method to return some type but may throw an exception, return a **Try[*that type*]** instead
