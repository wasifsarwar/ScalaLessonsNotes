package AdvancedScalaRockTheJVM.lectures.part2OOP

object Generics extends App {

  class MyList[A] {
    //use the type A
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
    */
  class ContravariantList[-A]
  val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]

  class Trainer[-A]

}
