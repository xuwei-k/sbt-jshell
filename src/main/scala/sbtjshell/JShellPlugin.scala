package sbtjshell

import javax.tools.Tool
import java.util.ServiceLoader
import sbt._
import sbt.Keys._
import sbt.complete.DefaultParsers._
import scala.collection.JavaConverters._

object JShellPlugin extends AutoPlugin {

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

  override val projectSettings: Seq[Def.Setting[_]] = Def.settings(
    Seq(Compile, Test).flatMap { c =>
      Def.settings(
        (c / jshell / fullClasspath) := {
          (c / fullClasspath).value.filter(_.data.exists)
        },
        (c / jshell) := {
          val args = spaceDelimited("<arg>").parsed.toList
          val path = (c / jshell / fullClasspath).value
            .map(_.data.getCanonicalPath)
            .mkString(System.getProperty("path.separator"))

          IO.withTemporaryFile("jshell-startup", ".jsh") { temp =>
            val startup = (c / jshell / initialCommands).?.value match {
              case Some(s) if s.trim.nonEmpty =>
                IO.write(temp, s)
                Seq("--startup", temp.getCanonicalPath)
              case _ =>
                Nil
            }
            System.out.synchronized {
              runJShell(Seq("--class-path", path) ++ startup ++ args)
            }
          }
        }
      )
    }
  )

}
