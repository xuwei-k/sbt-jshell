Compile / unmanagedSourceDirectories += baseDirectory.value.getParentFile / "src" / "main" / "scala"
Compile / unmanagedSourceDirectories += baseDirectory.value.getParentFile / "src" / "main" / "scala-3"

scalacOptions ++= Seq(
  "-Wconf:msg=it should not be used as infix operator:error",
  "-deprecation",
  "-unchecked",
  "-language:existentials",
  "-language:implicitConversions"
)

addSbtPlugin("com.github.sbt" % "sbt-pgp" % "2.4.0")
addSbtPlugin("com.github.sbt" % "sbt-release" % "1.6.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.6.2")

libraryDependencies += "com.github.xuwei-k" %% "scala-version-from-sbt-version" % "0.2.0"
