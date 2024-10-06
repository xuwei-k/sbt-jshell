package sbtjshell

import sbt.{Configuration, Def, Task}
import sbt.Keys.fullClasspath
import java.io.File

private[sbtjshell] trait JShellCompat { self: JShellPlugin.type =>
  import self.autoImport.*

  def jshellFullClasspath(c: Configuration): Def.Setting[?] = {
    (c / jshell / fullClasspath) := {
      (c / fullClasspath).value.filter(_.data.exists)
    }
  }

  def jshellFullClasspathValue(c: Configuration): Def.Initialize[Task[Seq[File]]] =
    Def.task(
      (c / jshell / fullClasspath).value.map(_.data)
    )
}
