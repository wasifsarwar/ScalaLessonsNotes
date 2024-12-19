package helper

object NumberParser {
  private val numberMap: Map[Int, String] =
    (0 to 100).map(n => n -> n.toString).toMap
  private val stringMap: Map[String, Int] = numberMap.map(_.swap)

  def parse(number: String): Option[Int] = stringMap.get(number)

  def toString(number: Int): Option[String] = numberMap.get(number)
}
