#!/bin/bash

# Define paths for microservices
USER_MS_DIR="./user-ms"
PATIENT_MS_DIR="./patient-ms"

# Define log directory
LOG_DIR="./log"

# Create log directory if it doesn't exist
if [ ! -d "$LOG_DIR" ]; then
  mkdir "$LOG_DIR"
  echo "Created log directory: $LOG_DIR"
fi

# Function to create a log file if it doesn't exist
create_log_file() {
  LOG_FILE=$1
  if [ ! -f "$LOG_FILE" ]; then
    touch "$LOG_FILE"
    echo "Created log file: $LOG_FILE"
  fi
}

# Function to start a microservice and redirect logs
start_microservice() {
  SERVICE_DIR=$1
  SERVICE_NAME=$2
  LOG_FILE="$LOG_DIR/${SERVICE_NAME,,}.log"

  echo "Starting $SERVICE_NAME..."
  if [ -d "$SERVICE_DIR" ]; then
    create_log_file "$LOG_FILE"  # Ensure log file exists
    (cd "$SERVICE_DIR" && mvn spring-boot:run > "$LOG_FILE" 2>&1) &
    echo "$SERVICE_NAME is starting... Logs are redirected to $LOG_FILE"
  else
    echo "Directory $SERVICE_DIR not found!"
  fi
}

# Start the user microservice
start_microservice "$USER_MS_DIR" "UserMicroservice"

# Start the patient microservice
start_microservice "$PATIENT_MS_DIR" "PatientMicroservice"

# Wait for all background processes to complete
wait
