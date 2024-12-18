package lunchAndLearnScala

class Number {
  class Number(protected val value: String) {
    override def toString: String = s"Number: $value"
    //def apply(number: Int): Int =
  }

  object Number {
    // factory Method
    def apply(value: String): Number = Number(value)

    def unapply(number: Number): Option[String] = Some(number.value)
  }
}
