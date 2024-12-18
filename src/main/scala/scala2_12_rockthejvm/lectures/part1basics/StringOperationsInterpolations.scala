package scala2_12_rockthejvm.lectures.part1basics

object StringOperationsInterpolations extends App{

  val str = "Hello, I am learning Scala"
  println(str.charAt(2))
  println(str.substring(5, 12))
  println(str.split(" ").toList)


  // Scala specific String Interpolators

  /**
   * S-Interpolators
   */
  val name = "David"
  val age = 12
  println(s"Hello, my name is $name and I'm ${age + 1} years old")

  /**
   * F-Interpolators
   * For formatted strings, similar to printF
   * f -> interpolated formatted string, $ expands variable value,
   */
  val speed = 1.2f
  val myth = f"$name%s can $speed%2.2f burgers per minute"
  println(myth)

  /**
   * Raw-Interpolators
   */
  println(raw"This a \n newline")
  val escaped = "This is a \n newline haha"
  println(raw"$escaped")
}
