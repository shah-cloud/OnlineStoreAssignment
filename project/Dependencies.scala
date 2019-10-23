import sbt._

object Dependencies {

  val scala: String = "2.12.8"
  val logbackClassicVersion: String = "1.2.3"
  val scalaTestVersion: String = "3.0.5"

  val logback: ModuleID = "ch.qos.logback" % "logback-classic" % logbackClassicVersion
  val config: ModuleID = "com.typesafe" % "config" % "1.2.1"
  val playJson: ModuleID = "com.typesafe.play" %% "play-json" % "2.8.0-M5"
  val slick: ModuleID = "com.typesafe.slick" %% "slick" % "3.3.2"
  val mysqlConnec: ModuleID = "mysql" % "mysql-connector-java" % "8.0.17"
  val slickDb: ModuleID = "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2"
  val akkaHttp: ModuleID = "com.typesafe.akka" %% "akka-http" % "10.1.10"
  val akkaStream: ModuleID = "com.typesafe.akka" %% "akka-stream" % "2.5.23"
  val akkaSprayJson: ModuleID = "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.10"
  val slickTest2: ModuleID = "com.typesafe.akka" %% "akka-http-testkit" % "10.1.10"
  val slickTest3: ModuleID = "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.23"
  val slickTest4: ModuleID = "org.postgresql" % "postgresql" % "42.2.5"
  val h2: ModuleID = "com.h2database" % "h2" % "1.4.192"

  /** Test libraries */
  val scalaTest: ModuleID = "org.scalatest" %% "scalatest" % scalaTestVersion

  val mock: ModuleID = "org.mockito" % "mockito-core" % "2.27.0"

  val dashboardDependencies: Seq[ModuleID] = Seq(slickTest2, slickTest3, slickTest4, akkaHttp, slickDb, akkaStream, h2, scalaTest, akkaSprayJson, mock, playJson, logback, config, slick, mysqlConnec)

}
