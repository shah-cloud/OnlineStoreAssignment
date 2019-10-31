// This plugin is used to format scala classes.
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")

// This plugin is used to check scala styles.
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

// This plugin is used to generate scala code coverage report.
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

// This is a flexible Scala linter.
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.2.1")

//This plugin is used for duplicate code detection using Copy/Paste Detector (CPD) from the PMD project.
addSbtPlugin("com.github.sbt" % "sbt-cpd" % "2.0.0")

//This plugin is used to automatically upload code quality reports on CodeSquad server.
addSbtPlugin("io.github.knoldus" %% "codesquad-sbt-plugin" % "0.2.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.10")
