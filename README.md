# sbt jshell plugin

[![Build Status](https://travis-ci.org/xuwei-k/sbt-jshell.svg?branch=master)](https://travis-ci.org/xuwei-k/sbt-jshell)

### requirement
- sbt 1.0
- Java 9

### setup

plugin.sbt

```scala
addSbtPlugin("com.github.xuwei-k" % "sbt-jshell" % "0.1.0")
```

build.sbt

```scala
enablePlugins(JShellPlugin)
```
