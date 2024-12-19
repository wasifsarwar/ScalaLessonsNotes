package AdvancedScalaRockTheJVM.lectures.part2OOP

object AbstractDataTypes extends App {

  /** Abstract members: Classes with unimplemented fields or classes are called abstract classes
    */

  abstract class Animal {
    val creatureType: String = "Creature Type" //non-abstract type
    def eat: Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"
    override def eat = println("Bark Crunch YUM")
  }

  /** Traits are the ultimate data types in scala
    * traits like abstract classes have abstract fields and methods
    * what's special about them is unlike abstract classes, traits can be inherited
    */
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal = "RAW meat"
  }

  class Crocodile extends Animal with Carnivore {
    override val creatureType: String = "croc"
    def eat: Unit = println("nomnomnom bloody")
    def eat(animal: Animal) = println(
      s"I am a crocodile and I am eating a ${animal.creatureType} "
    )

    override val preferredMeal: String = "PREY HAHA"
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)
  println(croc.preferredMeal)

  /** Traits VS Abstract Classes
    * Both Abstract classes and Traits can have abstrct AND non-abstract types
    * Traits do not have constructor parameters, abstract classes do
    * you can inherit one abstract class, but multiple trait inheritence is allowed
    * Traits are for behavior, Abstract classes is defined to describe a type of thing
    */

}
