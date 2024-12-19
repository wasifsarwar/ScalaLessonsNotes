package AdvancedScalaRockTheJVM.lectures.part2OOP

object Inheritance extends App {

  // Scala allows only single class inheritance
  sealed class Animal {
    val creatureType = "Wild"
    def eat = println("nom nom")
  }

  class Cat extends Animal {
    def crunch = {
      super.eat
      println("that's what my cat sound like")
    }
  } //Cat is the subclass, Animal is superclass
  val cat = new Cat

  cat.eat
  cat.crunch

  /** Scala also supports type substitution, which is broadly speaking polymorphism
    * method calls will refer to running the most overridden method in parent trait/class
    * Overriding means changing method logic in sub/child class
    * Overloading means changing method signature
    */

  val unknownAnimal: Animal = new Cat

  class Dog extends Animal {
    override val creatureType: String = "Domestic"
    override def eat = println("Dog is eating")
  }

  class Doggo(override val creatureType: String = "dogee") extends Animal
  val dog = new Dog
  val doge = new Doggo
  dog.eat
  println(dog.creatureType)
  println(s"${doge.creatureType}")

  // Type Substitution == polymorphism
  val unknownDog: Animal = new Dog

  /** preventing overrides
    * use final on method or entire class
    * seal the class
    * This means classes inherited in the same file, but you can't inherit sealed class in other files
    */

}
