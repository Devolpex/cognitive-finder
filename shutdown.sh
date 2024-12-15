# Function to stop a microservice
stop_microservice() {
  SERVICE_NAME=$1
  PID=$(ps -ef | grep "$SERVICE_NAME" | grep -v grep | awk '{print $2}')

  if [ -n "$PID" ]; then
    echo "Stopping $SERVICE_NAME with PID $PID..."
    kill -9 $PID
    echo "$SERVICE_NAME stopped."
  else
    echo "$SERVICE_NAME is not running."
  fi
}

# Stop the user microservice
stop_microservice "user-ms"

# Stop the patient microservice
stop_microservice "patient-ms"

echo "All specified microservices have been shut down."
