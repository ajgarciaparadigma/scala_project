package rdd

import org.apache.spark.{SparkConf, SparkContext}

object RddExercises {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("RddExercises").setMaster("local[1]")
    val sc = SparkContext.getOrCreate(conf)
    val raw_content = sc.textFile("files/2015-12-12.csv")

    println(raw_content.getClass())
    println(raw_content.count())

    //Show the Head (First n rows)
    raw_content.take(5).foreach(println)


    /*
    * We can also take samples randomly with takeSample method. With takeSample method, we can give three arguments and need to give at least two of them. They are "if replacement", "number of samples", and "seed" (optional).
    * */
    raw_content.takeSample(true, 5, seed=100).foreach(println)


    ///
    // Transformation
    // We may note that each row of the data is a character string
    // and it would be more convenient to have an array instead.So we use map to transform them and use take method to get the first three rows to check how the resutls look like
    ///
    var content = raw_content.map(x => x.split(','))
    content.take(2)


    //// remove the double quotation marks in the imported data
    def clean(x: Array[String]): Array[String] = {x.map(_.filter(_ != '"'))}
    content = content.map(clean)
    content.take(2)

    //map VS flatMap
    val text = Array("a b c", "d e", "f g h")

    sc.parallelize(text).map(_.split(' ')).collect()
    sc.parallelize(text).flatMap(_.split(" ")).collect()


    //Here I would like to know how many downloading records each package has. For example, for R package "Rcpp", I want to know how many rows belong to it.
    val package_count = content.map(x => (x(6), 1)).reduceByKey((a, b) => a + b)

    println(package_count.getClass)
    println(package_count.count)
    package_count.take(5).foreach(println)

    //With CountByKEY
    val package_count_2 = content.map(x => (x(6), 1)).countByKey()
    println(package_count_2.getClass)
    println(package_count_2("Rcpp"))
    println(package_count_2("ggplot2"))


    ///
    // Sorting
    // I may want to know the rankings of these packages based on how many downloads they have.
    // Then we need to use sortByKey method. Please note:
    ///
    package_count.map(x => (x._2, x._1)).sortByKey(true).take(5).foreach(println)
    package_count.map(x => (x._2, x._1)).sortByKey(false).take(5).foreach(println)
    package_count.sortBy(_._2).take(5).foreach(println)
    package_count.sortBy(_._2, false).take(5).foreach(println)
    package_count.sortBy(x => x._2, false).take(5).foreach(println)


    //Filter
    println(content.filter(x => (x(6) == "Rtts") & (x(8) == "CN")).count())

    content.filter(x => (x(6) == "Rtts") & (x(8) == "CN")).take(1)(0).foreach(println)

    val temp = content.filter(x => (x(6) == "Rtts") & (x(8) == "US")).collect()
    println(temp.getClass)
    temp(0).foreach(println)


    //Union same rdd
    println(raw_content.count())
    println(raw_content.union(raw_content).count())

    //intersection
    println(raw_content.intersection(raw_content).count())
    //Distinct
    println(raw_content.distinct.count())


    //Join
    // generate a new RDD in which the 'country' variable is KEY
    val content_modified=content.map(x => (x(8), 1))
    val local_mapping = Array(("DE", "Germany"), ("US", "United States"), ("CN", "China"), ("IN", "India"))

    val mapping = sc.parallelize(local_mapping)

    content_modified.join(mapping).takeSample(false, 5, seed = 1).foreach(println)

    content_modified.leftOuterJoin(mapping).takeSample(false, 5, seed = 1).foreach(println)

  }
}
