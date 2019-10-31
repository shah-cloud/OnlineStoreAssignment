import Dependencies._
import CommonSettings._
import scoverage.ScoverageKeys

name := "OnlineStore"

version := "1.0"

scalaVersion := scala

def compileDeps(deps: Seq[ModuleID]): Seq[ModuleID] = deps map(_ % "compile")
def testDeps(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")

val strategy = assemblyMergeStrategy in assembly := {
  case "META-INF/versions/9/module-info.class" => MergeStrategy.discard
  case PathList("org",   "apache", xs @ _*) => MergeStrategy.last
  case "about.html" => MergeStrategy.rename
  case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
  case "META-INF/mailcap" => MergeStrategy.last
  case "META-INF/mimetypes.default" => MergeStrategy.last
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case "javax/inject/Inject.class" => MergeStrategy.last
  case "javax/inject/Named.class" => MergeStrategy.last
  case "javax/inject/Provider.class" => MergeStrategy.last
  case "javax/inject/Qualifier.class" => MergeStrategy.last
  case "javax/inject/Scope.class" => MergeStrategy.last
  case "javax/inject/Singleton.class" => MergeStrategy.last
  case "org/aopalliance/aop/Advice.class" => MergeStrategy.last
  case "org/aopalliance/aop/AspectException.class" => MergeStrategy.last
  case "org/aopalliance/intercept/ConstructorInterceptor.class" => MergeStrategy.last
  case "org/aopalliance/intercept/ConstructorInvocation.class" => MergeStrategy.last
  case "org/aopalliance/intercept/Interceptor.class" => MergeStrategy.last
  case "org/aopalliance/intercept/Invocation.class" => MergeStrategy.last
  case "org/aopalliance/intercept/Joinpoint.class" => MergeStrategy.last
  case "org/aopalliance/intercept/MethodInterceptor.class" => MergeStrategy.last
  case "org/aopalliance/intercept/MethodInvocation.class" => MergeStrategy.last
  case "org/slf4j/impl/StaticLoggerBinder.class" => MergeStrategy.last
  case "org/slf4j/impl/StaticMDCBinder.class" => MergeStrategy.last
  case "org/slf4j/impl/StaticMarkerBinder.class" => MergeStrategy.last
  case "git.properties" => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

lazy val onlineStoreService = (
  baseProject("OnlineStore")
    settings(test in assembly := {}, strategy,libraryDependencies ++= compileDeps(dashboardDependencies) ++
    testDeps(scalaTest, mock),
    ScoverageKeys.coverageMinimum := 95,
    ScoverageKeys.coverageFailOnMinimum := false,
    ScoverageKeys.coverageExcludedPackages := "")
  )

lazy val root = (
  project.in(file(".")).settings(mainClass in(Compile, run) := Some("com.knoldus.onlinestoreservice.dashboard.DashboardImpl")
    , run := {
      (run in onlineStoreService in Compile).evaluated
    })
    aggregate (onlineStoreService)
  )
