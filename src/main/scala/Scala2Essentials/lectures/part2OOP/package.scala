package Scala2Essentials.lectures

/** Package Object can be 1 per package
  * for this package it will be: package object part2OOP {}
  * Package object will have the same name as the package it is created in
  * package object makes sayHello and MY_DOB visible across all scala files in this packagew
  */

package object part2OOP {
  def sayHello: Unit = println("Hello package")
  val MY_DOB = 11021997
}
