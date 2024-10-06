package sbtjshell

import sbt.{Configuration, Def, Task}
import sbt.Keys.fullClasspath
import java.io.File

private[sbtjshell] trait JShellCompat { self: JShellPlugin.type =>

  def jshellFullClasspath(c: Configuration): Def.Setting[?] = {
    (c / autoImport.jshell / fullClasspath) := {
      (c / fullClasspath).value.filter(_.data.exists)
    }
  }

  def jshellFullClasspathValue(c: Configuration): Def.Initialize[Task[Seq[File]]] =
    Def.task {
      (c / autoImport.jshell / fullClasspath).value.map(_.data)
    }
}
