# sbt jshell plugin

[![scaladoc](https://javadoc.io/badge2/com.github.xuwei-k/sbt-jshell_sbt2_3/javadoc.svg)](https://javadoc.io/doc/com.github.xuwei-k/sbt-jshell_sbt2_3)

### requirement
- Java 9 or higher

### setup

project/plugin.sbt

```scala
addSbtPlugin("com.github.xuwei-k" % "sbt-jshell" % "0.1.5")
```

build.sbt

```scala
enablePlugins(JShellPlugin)
```

