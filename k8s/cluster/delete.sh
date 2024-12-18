#!/bin/bash

# Exit on error
set -e

# Variables
CLUSTER_NAME="cog-cluster"

# Delete NGINX Ingress Controller
echo "Deleting NGINX Ingress Controller..."
kubectl delete -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml || true

# Delete the kind cluster
echo "Deleting kind cluster $CLUSTER_NAME..."
kind delete cluster --name $CLUSTER_NAME

echo "Kind cluster $CLUSTER_NAME and NGINX Ingress Controller have been deleted."
