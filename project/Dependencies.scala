import sbt._

object Dependencies {

  val scala: String = "2.12.8"
  val logbackClassicVersion: String = "1.2.3"
  val scalaTestVersion: String = "3.0.5"

  val logback: ModuleID = "ch.qos.logback" % "logback-classic" % logbackClassicVersion
  val config: ModuleID = "com.typesafe" % "config" % "1.2.1"
  val playJson: ModuleID = "com.typesafe.play" %% "play-json" % "2.8.0-M5"
  val slick = "com.typesafe.slick" %% "slick" % "3.3.2"
  val mysqlConnec = "mysql" % "mysql-connector-java" % "8.0.17"
  val slickDb = "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2"
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.1.10"
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.5.23"
  val akkaSprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.10"

  /** Test libraries */
  val scalaTest: ModuleID = "org.scalatest" %% "scalatest" % scalaTestVersion

  val mock: ModuleID = "org.mockito" % "mockito-core" % "2.27.0"

  val dashboardDependencies: Seq[ModuleID] = Seq(akkaHttp, slickDb,akkaStream, scalaTest, akkaSprayJson, mock, playJson, logback, config, slick, mysqlConnec)

}
