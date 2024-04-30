import scala.util.Try

object Seconds {

  def format(seconds: Int): Try[String] = Try {
    require(seconds >= 0, "Cannot format negative durations.")

    val s = seconds % 60
    val minutes = seconds / 60
    val m = minutes % 60
    val h = minutes / 60
    f"$h%02d:$m%02d:$s%02d"
  }

  def main(args: Array[String]): Unit = {

    val result = format(189956)

    println(result)

  }
}
