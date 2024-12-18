#!/bin/bash

# Create a kind cluster configuration file
cat <<EOF > kind-config.yaml
kind: Cluster
apiVersion: kind.x-k8s.io/v1alpha4
nodes:
  - role: control-plane
  - role: worker
    extraPortMappings:
      - containerPort: 80
        hostPort: 8080
  - role: worker
    extraPortMappings:
      - containerPort: 80
        hostPort: 8081
EOF

# Create the kind cluster using the config file
kind create cluster --config kind-config.yaml

# Verify the cluster nodes
kubectl get nodes
