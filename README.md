# sbt jshell plugin

- [API Documentation](https://oss.sonatype.org/service/local/repositories/releases/archive/com/github/xuwei-k/sbt-jshell_2.12_1.0/0.1.4/sbt-jshell-0.1.4-javadoc.jar/!/sbtjshell/index.html)

### requirement
- Java 9 or higher

### setup

project/plugin.sbt

```scala
addSbtPlugin("com.github.xuwei-k" % "sbt-jshell" % "0.1.4")
```

build.sbt

```scala
enablePlugins(JShellPlugin)
```

