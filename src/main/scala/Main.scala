import org.apache.spark.{SparkConf, SparkContext}

object Main {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Main").setMaster("local[1]")
    val sc = SparkContext.getOrCreate(conf)

    val data = Seq(
      (10, "Modric"),
      (11, "Rodrigo"),
      (12, "Camavinga")
    )

    val rdd = sc.parallelize(data)
    rdd.foreach(item => {
      println(item)
    })

  }
}
