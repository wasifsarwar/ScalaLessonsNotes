package AdvancedScalaRockTheJVM.lectures.part2OOP

object AnonymousClasses extends App {

  /** Key Takeaways
    * We can instantiate types and override fields or methods on the spot
    * Rules ->
    * * pass in required constructor arguments if needed
    * implement all abstract fields/methods
    *
    * this also works for traits or classes, whether they are abstract or not
    */

  abstract class Animal {
    def eat: Unit
  }

  /** Anonymous class
    * the following is equal to
    * class AnonymousClasses$anon$1 extends Animal {
    *    override def eat: Unit = println("LMAO")
    * }
    */
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("LMAO")
  }

  println(funnyAnimal.getClass)

  class Person(val name: String) {
    def sayHi = println(s"hi my name is $name what's yours?")
  }

  val jim = new Person("Jim") {
    override def sayHi: Unit = println(s"what's up i'm ${this.name}")
  }

  jim.sayHi

}
