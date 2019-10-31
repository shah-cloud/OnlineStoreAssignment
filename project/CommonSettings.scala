import sbt.Keys._
import sbt._
import scoverage.ScoverageKeys


object CommonSettings {

  lazy val projectSettings = Seq(
    organization := "Knoldus",
    scalaVersion := Dependencies.scala,
    resolvers ++= Seq("OJO Snapshots" at "https://oss.jfrog.org/oss-snapshot-local",
      "confluent" at "https://packages.confluent.io/maven/"),
    fork in Test := true,
    parallelExecution in Test := false,
    checksums in update := Nil,
    ScoverageKeys.coverageExcludedFiles := ""
  )

  def baseProject(name: String): Project = (
    Project(name, file(name))
      settings (projectSettings: _*)
    )

}
