import scala.collection.immutable.ListMap

object SortCountWords {

  def countWords(s: String): Map[String, Int] = {
    s.split(' ').filter(_ != "").groupMapReduce(identity)(_ => 1)(_ + _)
  }

  def main(args: Array[String]): Unit = {

    val input = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen. No sólo sobrevivió 500 años, sino que tambien ingresó como texto de relleno en documentos electrónicos, quedando esencialmente igual al original. Fue popularizado en los 60s con la creación de las hojas \"Letraset\", las cuales contenian pasajes de Lorem Ipsum, y más recientemente con software de autoedición, como por ejemplo Aldus PageMaker, el cual incluye versiones de Lorem Ipsum"

    val result = countWords(input)
    val mapOrdenado = ListMap(result.toSeq.sortBy(-_._2): _*)
    // Imprime el Map ordenado
    println(mapOrdenado)

  }
}
