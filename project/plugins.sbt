Compile / unmanagedSourceDirectories += baseDirectory.value.getParentFile / "src" / "main" / "scala"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions"
)

addSbtPlugin("com.github.sbt" % "sbt-pgp" % "2.3.0")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.12.1")
addSbtPlugin("com.github.sbt" % "sbt-release" % "1.4.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.2")
