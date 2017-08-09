import sbtrelease.ReleaseStateTransformations._

name := "sbt-jshell"

sbtPlugin := true

scalacOptions ++= (
  "-deprecation" ::
  "-unchecked" ::
  "-language:existentials" ::
  "-language:higherKinds" ::
  "-language:implicitConversions" ::
  Nil
)

val tagName = Def.setting {
  s"v${if (releaseUseGlobalVersion.value) (version in ThisBuild).value else version.value}"
}

val tagOrHash = Def.setting {
  if (isSnapshot.value) sys.process.Process("git rev-parse HEAD").lineStream_!.head
  else tagName.value
}

scalacOptions in (Compile, doc) ++= {
  Seq(
    "-sourcepath", baseDirectory.value.getAbsolutePath,
    "-doc-source-url", s"https://github.com/xuwei-k/sbt-jshell/tree/${tagOrHash.value}€{FILE_PATH}.scala"
  )
}

pomExtra := (
<url>https://github.com/xuwei-k/sbt-jshell</url>
<developers>
  <developer>
    <id>xuwei-k</id>
    <name>Kenji Yoshida</name>
    <url>https://github.com/xuwei-k</url>
  </developer>
</developers>
<scm>
  <url>git@github.com:xuwei-k/sbt-jshell.git</url>
  <connection>scm:git:git@github.com:xuwei-k/sbt-jshell.git</connection>
  <tag>{tagOrHash.value}</tag>
</scm>
)

licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommand("publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges,
)

enablePlugins(JShellPlugin)
