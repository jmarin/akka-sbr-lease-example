import BuildSettings._
import Dependencies._

lazy val akkaDeps = Seq(akkaCluster)

lazy val `sbr-lease` = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= akkaDeps
  )
