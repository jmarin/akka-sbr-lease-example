package sample.cluster

import java.net.InetAddress

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.cluster.ClusterEvent.ClusterDomainEvent
import akka.cluster.{Cluster, ClusterEvent}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.management.cluster.bootstrap.ClusterBootstrap
import akka.management.scaladsl.AkkaManagement
import akka.stream.ActorMaterializer

object DemoApp extends App {

  implicit val system = ActorSystem("akka-sbr-lease-example")

  import system.log
  implicit val mat = ActorMaterializer()
  val cluster = Cluster(system)

  log.info(s"Started [$system], cluster.selfAddress = ${cluster.selfAddress}")

  //#start-akka-management
  AkkaManagement(system).start()
  //#start-akka-management
  ClusterBootstrap(system).start()

  cluster.subscribe(
    system.actorOf(Props[ClusterWatcher]),
    ClusterEvent.InitialStateAsEvents,
    classOf[ClusterDomainEvent]
  )

  // add real app routes here
  val routes =
    path("hello") {
      get {
        complete(
          HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            s"<h1>Hello from ${InetAddress.getLocalHost.getHostName}</h1>"
          )
        )
      }
    }
  Http().bindAndHandle(routes, "0.0.0.0", 8080)

  Cluster(system).registerOnMemberUp({
    log.info("Cluster member is up!")
  })

}

class ClusterWatcher extends Actor with ActorLogging {
  val cluster = Cluster(context.system)

  override def receive = {
    case msg ⇒ log.info(s"Cluster ${cluster.selfAddress} >>> " + msg)
  }

}
