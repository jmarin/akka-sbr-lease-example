import sbt.Keys._
import sbt._

object BuildSettings {

  val buildVersion = "1.0.0"
  val buildScalaVersion = "2.12.8"

  val commonSettings = Defaults.coreDefaultSettings ++
    Seq(
      version := buildVersion,
      scalaVersion := buildScalaVersion,
      scalacOptions ++= Seq(
        "-Xlint",
        "-deprecation",
        "-unchecked",
        "-feature",
        "-Ypartial-unification",
        "-language:higherKinds"
      ),
      parallelExecution in Test := true,
      fork in Test := true
    ) 

}
