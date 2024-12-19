package RockTheJVMbasics

object ContextualAbstractions {

  /**    1 - context parameters/arguments
    */

  val aList = List(2, 1, 3, 4)
  val orderedList = aList.sorted

  /**    - Ordering
    */

  def main(args: Array[String]): Unit = {
    println(orderedList)
  }
}
