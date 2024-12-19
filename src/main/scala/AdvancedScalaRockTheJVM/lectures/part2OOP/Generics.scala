package AdvancedScalaRockTheJVM.lectures.part2OOP

object Generics extends App {

  /** Takeaways 1
    * Use the same code on many (potentially unrelated) types
    * Generic methods -> adding a type parameter next of method signature before params with [A]
    * Multiple type parameters allowed -> Map[Key, Value]
    */

  /** Takeaways 2 - Variance: if subclass A extends parent class B, should List[A] extend List[B]
    * trait List[+A] -> yes(covariant)
    * trait List[A] -> no (invariant) so both types need to match -> List[A] = new List[A]
    * trait List[-A] -> hell no! (contravariant) if A extends B, then super class List[B] extends the subclass List[A]
    */

  /** Takeaways 3 - Bounded Types
    *  < : this is upper bound
    *  >: this is lower bound
    */

  class MyList[+A] {

    /** @param element
      * @tparam B which is a super type of A
      * @return
      */

    /** B >: A means that B must be a supertype of A.
      * This allows the method to accept elements of type A or any of its supertypes, ensuring type safety while allowing flexibility in the types that can be added to the list.
      */
    def add[B >: A](element: B): MyList[B] = ???

    /*
      let's say A = Cat, B = Animal
     */

  }

  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]
  val listOfLists = new MyList[MyList[Int]]

  class MyMap[Key, Value] {
    //
  }

  /** Generic Methods
    */

  object MyList {
    def empty[A]: MyList[A] = ???

  }

  val emptyListOfInts = MyList.empty[Int]

  /** Variance Problem
    */

  class Animal
  class Cat extends Animal
  class Dog extends Animal

  /** Covariant List
    * To make a covariant list of generic types, do [+A] !!!!!
    * a list of cat extends list of animals; List[Cat] -> List[Animal] == COVARIANCE
    * here, animal being a covariant list, and cat being of type Animal and instantiated as Cat
    * list[Animal] can be instantiated as list[Cat]
    */
  class CovariantList[+A]
  val cat: Animal = new Cat
  val animals: CovariantList[Animal] = new CovariantList[Cat]

  /** Invariant List; List[Animal] -> List[Animal]
    * to add a dog to list of animals, list of animals need to be instantiated as invariant type. It is simply [A] !!!
    * Both type and instantiation on the right side needs to match
    * MyList above holds invariant type
    */
  class InvariantList[A]
  val differentAnimals: InvariantList[Animal] = new InvariantList[Animal]

  /** Contravariant List;
    *  The subclass is the type, and is instantiated as the parent/super class
    */
  class ContravariantList[-A]
  val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]

  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  /** Bounded Types
    * < : is upper bounded type, ignore the space
    * >: is lower bounded type
    */

  class Cage[A <: Animal](animal: A)
  val cage = new Cage(new Dog)

  // example of Cage not accepting anything that isn't a subclass of Animal
  class Car
  // val carCage = new Cage[new Car]

  class CarCage[A >: Animal](animal: A)
  val carCage = new CarCage(new Car)
  // expand MyList to be generic
}
