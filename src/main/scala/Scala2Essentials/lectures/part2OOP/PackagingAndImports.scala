package Scala2Essentials.lectures.part2OOP

import RockTheJVMbasics.ObjectOrientation.{Crocodile => croc} //aliasing

object PackagingAndImports extends App {

  /**
   * Package = a group of definitions under the same name
   * Packages are in hierarchy
   * matching folder structures
   */

  /**
   * To use a definition
   * be in the same package
   * or import the package from which you want the definition from
   */

  /**
   * Package objects hold standalone methods/constants
   * Package Object can be 1 per package
   * for this package it will be: package object part2OOP {}
   * Package object will have the same name as the package it is created in
   */

  /**
   * you can call Writer directly because it's in another scala file under the same package
   */
  val writer = new Writer(surName = "Bhaiya", firstName = "Fuad", authorDOB = 1996)

  /**
   * Crocodile is in RockTheJVMbasics so that package had to be imported
   */
  val croc = new croc
  sayHello
  println(MY_DOB)

}
