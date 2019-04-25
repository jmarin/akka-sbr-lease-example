# SBR Lease Strategy Sample

## Running

This project is based on the Akka Samples cluster example. To run in a `Kubernetes` environment:

* First create a local `docker` image:

```shell
sbt docker:publishLocal
```

* Apply `kubernetes` configuration:

```shell
kubectl apply -f kubernetes/crd.yml
kubectl apply -f kubernetes/akka-cluster.yml
```

To see Split Brain Resolver in action, follow the logs of the three pods created in this
configuration and kill one of the pods:

```shell
kubectl delete pod <POD_ID>
```

One of the other two will show a log similar to the one below, where SBR has determined
that a node is no longer part of the cluster, thus being removed

```
04/25 12:26:19 INFO [akka-sbr-lease-example-akka.actor.default-dispatcher-24] c.l.a.s.SplitBrainResolver - SBR acquired lease for decision [AcquireLeaseAndDownUnreachable(0 days)]
04/25 12:26:19 WARN [akka-sbr-lease-example-akka.actor.default-dispatcher-17] c.l.a.s.SplitBrainResolver - SBR took decision AcquireLeaseAndDownUnreachable(0 days) and is downing [akka.tcp://akka-sbr-lease-example@172.17.0.10:2552],
 [1] unreachable of [4] members, all members in DC [Member(address = akka.tcp://akka-sbr-lease-example@172.17.0.10:2552, status = Up), Member(address = akka.tcp://akka-sbr-lease-example@172.17.0.11:2552, status = Up),
 Member(address = akka.tcp://akka-sbr-lease-example@172.17.0.12:2552, status = Up), Member(address = akka.tcp://akka-sbr-lease-example@172.17.0.9:2552, status = Up)],
 full reachability status: akka.tcp://akka-sbr-lease-example@172.17.0.11:2552 -> akka.tcp://akka-sbr-lease-example@172.17.0.10:2552: Unreachable [Unreachable] (4), akka.tcp://akka-sbr-lease-example@172.17.0.12:2552 -> akka.tcp://akka-sbr-lease-example@172.17.0.10:2552: Unreachable [Unreachable] (2),
 akka.tcp://akka-sbr-lease-example@172.17.0.9:2552 -> akka.tcp://akka-sbr-lease-example@172.17.0.10:2552: Unreachable [Unreachable] (1)
04/25 12:26:19 INFO [akka-sbr-lease-example-akka.actor.default-dispatcher-17] a.c.Cluster(akka://akka-sbr-lease-example) - Cluster Node [akka.tcp://akka-sbr-lease-example@172.17.0.11:2552] - Marking unreachable node [akka.tcp://akka-sbr-lease-example@172.17.0.10:2552] as [Down]
04/25 12:26:19 INFO [akka-sbr-lease-example-akka.actor.default-dispatcher-3] s.c.ClusterWatcher - Cluster akka.tcp://akka-sbr-lease-example@172.17.0.11:2552 >>> MemberDowned(Member(address = akka.tcp://akka-sbr-lease-example@172.17.0.10:2552, status = Down))
```

To see the leases that get created in `Kubernetes`:

```shell
kubectl get le
```