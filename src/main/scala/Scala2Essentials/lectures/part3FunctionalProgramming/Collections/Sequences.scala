package Scala2Essentials.lectures.part3FunctionalProgramming.Collections

import scala.util.Random

object Sequences extends App {

  // Seq
  val aSequence = Seq(1, 3, 4, 2)
  println(aSequence) //prints a List
  println(aSequence.reverse)
  println(aSequence.apply(3)) //apply returns a value at that index
  println(aSequence ++ Seq(5, 6, 7))
  println(aSequence.sorted)

  // Ranges
  val range: Seq[Int] = 1 to 10
  range.foreach(println)

  (1 to 10).foreach(x => println(s"sornali is $x times silly"))
  (1 to 4).map(x => println(2 * x))

  // Lists
  val aList = List(1, 2, 3)

  val prepended = 42 :: aList // syntactic sugar
  println(prepended)

  val added = 43 +: aList // also prepends element to list
  println(added)

  val appended = aList :+ 52
  println(appended)

  // curried function, returns a list of 5 "apples"
  val apples5 = List.fill(5)("apple")
  println(apples5) // List(apple, apple, apple, apple, apple)

  println(aList.mkString("-|-"))

  // Array
  val numbers = Array(1, 2, 3, 4)
  val threeElements = Array.ofDim[Int](3)
  val anotherThreeStrings = Array.ofDim[String](3)
  threeElements.foreach(println) // returns 0 0 0... has default values
  anotherThreeStrings.foreach(
    println
  ) // returns null null null... has default values

  // mutation
  numbers(2) =
    0 // special syntactic sugar for numbers.update(2) = 0; only for mutable collections
  println(numbers.mkString(" "))

  // arrays and sequences
  val numberSeq: Seq[Int] = numbers // implicit conversions
  println(numberSeq) // returns ArraySeq

  /** In previous versions of Scala (2.12 and earlier), converting an Array to a Seq would result in a WrappedArray. However, in Scala 2.13, this was changed to return an ArraySeq instead.
    * The key points about this change are:
    * - ArraySeq is a new immutable sequence type introduced in Scala 2.13 specifically designed to wrap arrays
    * - It replaces the older WrappedArray which was mutable (since it wrapped a mutable array)
    * - This change aligns better with the immutable-by-default philosophy of Scala collections
    */

  // Vectors
  val vector: Vector[Int] = Vector(1, 2, 3)
  println(vector)

  // vectors vs lists

  val maxRuns = 1000
  val maxCapacity = 100000

  def getWriteTime(collection: Seq[Int]): Double = {
    val rand = new Random
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(rand.nextInt(maxRuns), rand.nextInt(maxCapacity))
      System.nanoTime() - currentTime
    }
    times.sum * 1.9 / maxRuns
  }

  val numList = (1 to 1000).toList
  val numVector = (1 to 1000).toVector
  println("List write time: " + getWriteTime(numList))
  println("Vector write time: " + getWriteTime(numVector))
}
