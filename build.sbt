name := "OnlineStore-service"

version := "0.1"

scalaVersion := "2.13.1"

//libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "1.1.1"

libraryDependencies += "com.typesafe.slick" %% "slick" % "3.3.2"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.17"
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2"
//libraryDependencies += "ch.qos.logback" % "logback-classic" % logbackClassicVersion
libraryDependencies +=  "com.typesafe.akka" %% "akka-http" % "10.1.10"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.0-M5"

libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.10"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.23"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.10"
