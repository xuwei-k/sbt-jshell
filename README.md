# sbt jshell plugin

- [API Documentation](https://oss.sonatype.org/service/local/repositories/releases/archive/com/github/xuwei-k/sbt-jshell_2.12_1.0/0.1.3/sbt-jshell-0.1.3-javadoc.jar/!/sbtjshell/index.html)

### requirement
- Java 9 or higher

### setup

project/plugin.sbt

```scala
addSbtPlugin("com.github.xuwei-k" % "sbt-jshell" % "0.1.3")
```

build.sbt

```scala
enablePlugins(JShellPlugin)
```

