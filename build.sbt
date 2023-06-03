import sbtrelease.ReleaseStateTransformations._

ThisBuild / onChangedBuildSource := ReloadOnSourceChanges

organization := "com.github.xuwei-k"

name := "sbt-jshell"

sbtPlugin := true

sbtPluginPublishLegacyMavenStyle := {
  sys.env.isDefinedAt("GITHUB_ACTION") || isSnapshot.value
}

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions"
)

val tagName = Def.setting {
  s"v${if (releaseUseGlobalVersion.value) (ThisBuild / version).value else version.value}"
}

val tagOrHash = Def.setting {
  if (isSnapshot.value) sys.process.Process("git rev-parse HEAD").lineStream_!.head
  else tagName.value
}

(Compile / doc / scalacOptions) ++= {
  Seq(
    "-sourcepath",
    baseDirectory.value.getAbsolutePath,
    "-doc-source-url",
    s"https://github.com/xuwei-k/sbt-jshell/tree/${tagOrHash.value}€{FILE_PATH}.scala"
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

releaseTagName := tagName.value

val updateReadme: State => State = { state: State =>
  val sonatypeURL = "https://oss.sonatype.org/service/local/repositories/"
  val extracted = Project.extract(state)
  val scalaV = extracted get scalaBinaryVersion
  val sbtV = extracted get sbtBinaryVersion
  val v = extracted get version
  val org = extracted get organization
  val n = extracted get name
  val snapshotOrRelease = if (extracted get isSnapshot) "snapshots" else "releases"
  val readme = "README.md"
  val readmeFile = file(readme)
  val newReadme = Predef
    .augmentString(IO.read(readmeFile))
    .lines
    .map { line =>
      val matchReleaseOrSnapshot = line.contains("SNAPSHOT") == v.contains("SNAPSHOT")
      if (line.startsWith("addSbtPlugin") && matchReleaseOrSnapshot) {
        s"""addSbtPlugin("${org}" % "${n}" % "$v")"""
      } else if (line.contains(sonatypeURL) && matchReleaseOrSnapshot) {
        s"- [API Documentation](${sonatypeURL}${snapshotOrRelease}/archive/${org.replace('.', '/')}/${n}_${scalaV}_${sbtV}/${v}/${n}-${v}-javadoc.jar/!/sbtjshell/index.html)"
      } else line
    }
    .mkString("", "\n", "\n")
  IO.write(readmeFile, newReadme)
  val git = new sbtrelease.Git(extracted get baseDirectory)
  git.add(readme) ! state.log
  git.commit(message = "update " + readme, sign = false, signOff = false) ! state.log
  sys.process.Process(Seq("git", "diff", "HEAD^")).!
  state
}

commands += Command.command("updateReadme")(updateReadme)

val updateReadmeProcess: ReleaseStep = updateReadme

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  updateReadmeProcess,
  tagRelease,
  releaseStepCommandAndRemaining("set ThisBuild / useSuperShell := false"),
  releaseStepCommandAndRemaining("^ publishSigned"),
  releaseStepCommandAndRemaining("set ThisBuild / useSuperShell := true"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  updateReadmeProcess,
  pushChanges,
)

publishTo := sonatypePublishToBundle.value

enablePlugins(JShellPlugin)
