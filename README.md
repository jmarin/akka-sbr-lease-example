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

