package Scala2Essentials.lectures.part2OOP

object OOBasicsExercise extends App {

  val wasif = new Writer("Wasif", "Siddique", 27)
  val novel = new Novel("Jouboner Khel", 2016, wasif)
  if (novel.isWrittenBy(wasif))
    println(s"yes, ${wasif.apply()} wrote ${novel.novelName}")
  else (println(s"no, this author didn't write ${novel.novelName}"))
  println(
    s"the author of this book ${novel.author.apply()} is ${novel.authorAge()} years old}"
  )
  novel.copy(2020)
  println(novel.isWrittenBy(new Writer("John", "Tron", 34)))

  val counter = new MyCounter(12)
  println(counter.increment().increment().increment().decrement().input)

}

/* Scala Exercise */

/** Novel and a Writer
  * Writer: first name, surname, year
  *  - method fullname
  * Novel: name, year of release, author
  *  - authorAge
  *  - isWrittenBy(author)
  *  - copy (new year of release) = new instance of Novel
  */

class Writer(val firstName: String, val surName: String, val authorDOB: Int) {
  def apply(): String = {
    s"$firstName $surName"
  }
}

class Novel(val novelName: String, var releaseYear: Int, val author: Writer) {
  def authorAge(): Int = releaseYear - author.authorDOB
  def isWrittenBy(author: Writer): Boolean = author == this.author
  def copy(newYear: Int) = new Novel(this.novelName, newYear, this.author)
}

/** Counter Class
  * - recieves an Int value
  * - method current count
  * - method to increment/decerement => which will update current count
  * - overload inc/dec to receive an amount
  */

// notice that by specifying input as val, we can always refer to it as MyCounter's current field

class MyCounter(val input: Int = 0) {

  def increment() = {
    println(s"incrementing $input")
    new MyCounter(input + 1)
  }

  def decrement() = {
    println(s"decrementing $input")
    new MyCounter(input - 1)
  }
  def increment(n: Int): MyCounter = {
    if (n <= 0) this
    else increment.increment(n - 1)
  }

  def decrement(n: Int): MyCounter = {
    if (n <= 0) this
    new MyCounter(input - n)
  }
}
