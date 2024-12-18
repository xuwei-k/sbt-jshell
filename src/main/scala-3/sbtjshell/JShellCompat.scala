package sbtjshell

import sbt.*
import sbt.Keys.fullClasspath
import sbt.Keys.fileConverter
import java.io.File

private[sbtjshell] trait JShellCompat { self: JShellPlugin.type =>

  def jshellFullClasspath(c: Configuration): Def.Setting[?] = {
    (c / autoImport.jshell / fullClasspath) := {
      val converter = fileConverter.value
      (c / fullClasspath).value.filter(x => converter.toPath(x.data).toFile.exists)
    }
  }

  def jshellFullClasspathValue(c: Configuration): Def.Initialize[Task[Seq[File]]] =
    Def.task {
      val converter = fileConverter.value
      (c / autoImport.jshell / fullClasspath).value.map(x => converter.toPath(x.data).toFile)
    }
}
