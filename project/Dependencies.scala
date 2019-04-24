import sbt._
import Version._

object Dependencies {

  lazy val akkaSlf4J = "com.typesafe.akka" %% "akka-slf4j" % Version.akka
  lazy val logback = "ch.qos.logback" % "logback-classic" % Version.logback

  lazy val akkaClusterSharding = "com.typesafe.akka" %% "akka-cluster-sharding" % Version.akka
  lazy val akkaPersistence = "com.typesafe.akka" %% "akka-persistence" % Version.akka
  lazy val akkaClusterBootstrap = "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % Version.akkaManagement
  lazy val akkaManagementCluster = "com.lightbend.akka.management" %% "akka-management-cluster-http" % Version.akkaManagement
  lazy val akkaDiscovery = "com.typesafe.akka" %% "akka-discovery" % Version.akka
  lazy val sbr = "com.lightbend.akka" %% "akka-split-brain-resolver" % Version.sbr
}
