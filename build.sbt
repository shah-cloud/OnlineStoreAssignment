import Dependencies._

name := "OnlineStore"

version := "1.0"

scalaVersion := scala

def compileDeps(deps: Seq[ModuleID]): Seq[ModuleID] = deps
def testDeps(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")

libraryDependencies ++= compileDeps(dashboardDependencies) ++
    testDeps(scalaTest, mock)