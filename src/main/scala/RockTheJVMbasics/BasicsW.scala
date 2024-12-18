package RockTheJVMbasics

object BasicsW extends App {

  /*
    defining a value (think of constants, java eq const int meaningOfLife = 42;
    You can't redefine a val
    type mentioning is optional for most cases
   */
  val meaningOfLife: Int = 42

  // Int, Boolean, Char, Double, Float, String
  val aBoolean = false

  val aString = "hahha scala"
  val aComposedString = aString + "interesting"
  val interpolatedString = s"What he said was $aString \n $aComposedString"

  // expressions = structures that can be reduced to a value
  val anExpression = 2 + 3

  //if expression
  val ifExpression = if (meaningOfLife > 43) 56 else 999
  val chainedIfExpression = {
    if (meaningOfLife > 43) 56
    else if (meaningOfLife < 0) -2
    else if (meaningOfLife > 999) 992
    else 0
  }

  val aCodeBlock = {
    //defintions
    val aLocalValue = 67

    //last expression determines entire value of the code block
    aLocalValue + 3
  }

  def myFunction(x: Int, y: String): String = {
    y + " " + x
  }

  // recursive function
  def factorial(n : Int): Int =
    if (n <= 1) 1
    else n * factorial(n-1)

  // in scala there's no loops or iteration, only recursion
  // the Unit type = no meaningful value == "void" 

  println(interpolatedString)
  println(chainedIfExpression)
}
