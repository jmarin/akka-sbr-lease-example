import Dependencies._
import BuildSettings._
import com.typesafe.sbt.packager.docker._

enablePlugins(JavaServerAppPackaging, sbtdocker.DockerPlugin, AshScriptPlugin, Cinnamon)

packageName in Docker := "akka-sbr-lease-example"

version := buildVersion

dockerExposedPorts := Seq(8080, 8558, 2552)

dockerBaseImage := "openjdk:8-jre"

mainClass in Compile := Some("sample.cluster.DemoApp")

dockerCommands :=
  dockerCommands.value.flatMap {
    case ExecCmd("ENTRYPOINT", args @ _*) =>
      Seq(Cmd("ENTRYPOINT", args.mkString(" ")))
    case v => Seq(v)
  }

javaOptions in Universal ++= Seq(
  "-J-XX:+UnlockExperimentalVMOptions",
  "-J-XX:+UseCGroupMemoryLimitForHeap"
)

javaOptions in reStart ++= (javaOptions in run).value

libraryDependencies ++= Seq(akkaSlf4J, logback, akkaDiscovery, akkaPersistence, akkaClusterSharding, akkaManagementCluster, akkaClusterBootstrap, sbr, kubernetesLease)
