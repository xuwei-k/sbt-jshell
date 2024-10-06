package sbtjshell

import sbt.{Configuration, Def,  given }
import sbt.Keys.fullClasspath
import sbt.Keys.fileConverter

private[sbtjshell] trait JShellCompat { self: JShellPlugin.type =>
  import self.autoImport.*

  def jshellFullClasspath(c: Configuration): Def.Setting[?] = {
    (c / jshell / fullClasspath) := {
      val converter = fileConverter.value
      (c / fullClasspath).value.filter(x => converter.toPath(x.data).toFile.exists)
    }
  }

  def jshellFullClasspathValue(c: Configuration) = Def.task{
    val converter = fileConverter.value
    (c / jshell / fullClasspath).value.map(x => converter.toPath(x.data).toFile)
  }
}
