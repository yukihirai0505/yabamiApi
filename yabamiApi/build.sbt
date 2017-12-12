name := "yabamiApi"
version := "1.0.0"
scalaVersion := "2.11.7"

assemblyOutputPath in assembly := file("./api-standalone.jar")

libraryDependencies ++= {
  val akkaV = "10.0.7"
  val slickVersion = "3.2.0-M2"
  val circeV = "0.6.1"
  val scalaTestV = "3.0.1"
  Seq(

    "commons-codec" % "commons-codec" % "1.10",

    "com.typesafe.akka" %% "akka-http-core" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaV,
    "de.heikoseeberger" %% "akka-http-circe" % "1.11.0",

    // DB
    "com.typesafe.slick" %% "slick" % slickVersion,
    "mysql" % "mysql-connector-java" % "5.1.42",
    "org.flywaydb" % "flyway-core" % "3.2.1",

    // DB Option
    "com.github.tototoshi" %% "slick-joda-mapper" % "2.3.0",

    "com.zaxxer" % "HikariCP" % "2.4.5",

    "io.circe" %% "circe-core" % circeV,
    "io.circe" %% "circe-generic" % circeV,
    "io.circe" %% "circe-parser" % circeV,

    // Twitter
    "com.danielasfregola" %% "twitter4s" % "5.3",

    // iService
    "com.yukihirai0505" % "iservice_2.11" % "2.4.1",

    // TypeSafe config
    "com.typesafe" % "config" % "1.3.2",

    // Joda time
    "joda-time" % "joda-time" % "2.9.3",
    "org.joda" % "joda-convert" % "1.8.1",

    "org.scalatest" %% "scalatest" % scalaTestV % "test",
    "com.typesafe.akka" %% "akka-http-testkit" % akkaV % "test"
  )
}

Revolver.settings
enablePlugins(JavaAppPackaging)
