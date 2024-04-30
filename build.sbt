ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.1"
)

lazy val root = (project in file("."))
  .settings(
    name := "scala_project"
  )
