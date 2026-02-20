package sbtjshell

import javax.tools.Tool
import java.util.ServiceLoader
// format: off
import sbt.{given, *}
// format: on
import sbt.Keys._
import sbt.complete.DefaultParsers._
import scala.collection.JavaConverters._

object JShellPlugin extends AutoPlugin with JShellCompat {

  object autoImport {
    val jshell = inputKey[Int]("invoke jshell")
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

  System.setProperty("sbt.supershell", "false")

  override val projectSettings: Seq[Def.Setting[?]] = Def.settings(
    Seq(Compile, Test).flatMap { c =>
      Def.settings(
        jshellFullClasspath(c),
        (c / jshell) := {
          val args = spaceDelimited("<arg>").parsed.toList
          val path = jshellFullClasspathValue(c).value.map(_.getAbsolutePath).mkString(java.io.File.pathSeparator)

          IO.withTemporaryFile("jshell-startup", ".jsh") { temp =>
            val startup = (c / jshell / initialCommands).?.value match {
              case Some(s) if s.trim.nonEmpty =>
                IO.write(temp, s)
                Seq("--startup", temp.getCanonicalPath)
              case _ =>
                Nil
            }
            runJShell(Seq("--class-path", path) ++ startup ++ args)
          }
        }
      )
    }
  )

}
