# sbt jshell plugin

[![Build Status](https://travis-ci.org/xuwei-k/sbt-jshell.svg?branch=master)](https://travis-ci.org/xuwei-k/sbt-jshell)

- [API Documentation](https://oss.sonatype.org/service/local/repositories/releases/archive/com/github/xuwei-k/sbt-jshell_2.12_1.0/0.1.0/sbt-jshell-0.1.0-javadoc.jar/!/sbtjshell/index.html)

### requirement
- sbt 1.0
- Java 9

### setup

project/plugin.sbt

```scala
addSbtPlugin("com.github.xuwei-k" % "sbt-jshell" % "0.1.0")
```

build.sbt

```scala
enablePlugins(JShellPlugin)
```
