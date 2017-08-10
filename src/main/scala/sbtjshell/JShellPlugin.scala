package sbtjshell

import javax.tools.Tool
import java.util.ServiceLoader
import sbt._
import sbt.Keys._
import sbt.complete.DefaultParsers._
import scala.collection.JavaConverters._

object JShellPlugin extends AutoPlugin {

  object autoImport {
    val jshell = inputKey[Unit]("invoke jshell")
  }

  import autoImport._

  def findJShell(): Tool = {
    val loader = ServiceLoader.load(classOf[Tool])
    val tools = loader.iterator.asScala.toList
    tools.find(_.name() == "jshell").getOrElse(sys.error("could not found jshell " + tools))
  }

  def runJShell(args: Seq[String]): Int = {
    findJShell().run(null, null, null, args: _*)
  }

  override val projectSettings: Seq[Def.Setting[_]] = Seq[Def.SettingsDefinition](
    Seq(Compile, Test).map { c =>
      (jshell in c) := {
        val args = spaceDelimited("<arg>").parsed.toList
        val path = (fullClasspath in c).value
          .collect{ case x if x.data.exists => x.data.getCanonicalPath }
          .mkString(System.getProperty("path.separator"))
        runJShell(Seq("--class-path", path) ++ args)
      }
    }
  ).flatMap(_.flatten)

}
