# Cognitive Finder: Localization of Patients with Cognitive Disorders

## Introduction

Cognitive disorders, such as Alzheimer's disease, pose significant challenges to patient safety, especially with the risk of wandering or elopement. This project proposes a solution using wearable sensors to monitor patients with cognitive impairments in real-time. The system tracks patient movements and detects when a patient leaves a predefined safe zone or exhibits unusual movement behaviors. Alerts are triggered to caregivers to enable prompt intervention, improving patient security and preventing dangerous situations.

## Features

- **Real-time Monitoring**: The system tracks patient movements using wearable sensors, detecting when the patient leaves a safe zone or exhibits unusual behavior.
- **Immediate Alerts**: Caregivers receive instant alerts through the notification service when a patient is at risk.
- **Microservices Architecture**: The backend consists of various microservices, including patient management, tracking, and notifications, ensuring scalability and maintainability.
- **Cloud Infrastructure**: Deployed in the cloud with Kubernetes and Docker for scaling, and CI/CD pipeline integration for continuous deployment.
- **Multiple Frontends**: The system uses Angular and Flutter for the frontend to provide responsive and accessible interfaces for caregivers.

## How to Install and Run

### Prerequisites

1. **Docker**: Ensure Docker and Docker Compose are installed.
2. **JDK 21.0.5**: Required for Spring Boot applications.
3. **Node.js v18.20.4**: Required for Angular applications.
4. **Angular 18.2.9**: Installed globally via npm.
5. **MySQL 8.0**: For managing patient and tracking data.

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/Devolpex/cognitive-finder
   cd cognitive-finder
   ```

2. Set up Docker containers for services:
   ```bash
   docker-compose -f docker/compose.yml up -d
   ```

3. Start the frontend applications:
   - Angular frontend:
     ```bash
     cd frontend
     npm install
     ng serve
     ```
   - Flutter frontend:
     ```bash
     cd frontend/flutter-app
     flutter run
     ```

4. Access the system through the web interface:
   - **Angular**: Navigate to `http://localhost:4200`
   - **Flutter**: Access via mobile or emulator.

## Structure of Repositories

The project is organized into various services, each responsible for specific functionality:

```bash
cognitive-finder/
│
├── api-gateway/                # API Gateway for routing requests to microservices
├── compose.yml                 # Docker Compose file for container orchestration
├── docker/                     # Docker-related files
├── docs/                       # Documentation files
├── frontend/                   # Frontend code (Angular & Flutter)
├── lib/                        # Shared libraries used by multiple microservices
├── notification-ms/            # Notification microservice for alerts
├── patient-ms/                 # Patient management microservice
├── production/                 # Production deployment configurations
├── tracking-ms/                # Tracking microservice for GPS data and alerts
├── user-ms/                    # User management microservice
└── README.md                   # Project ReadMe file

```

### Key Services

1. **API Gateway**: Routes requests to appropriate microservices and handles service discovery.
2. **Patient Management Service**: Manages patient data using MongoDB.
3. **Tracking Service**: Handles GPS tracking data from wearable devices and stores it in PostgreSQL.
4. **Notification Service**: Sends alerts to caregivers when a patient is at risk.
5. **User Service**: Manages user authentication and authorization through Keycloak.

## Architecture

### Microservices Architecture

This system uses a microservices architecture, where each service is responsible for a specific task and interacts with others via APIs. The services are loosely coupled, allowing for easy scaling and maintenance. The microservices are:

- **Patient Management**: Stores and manages patient data.
- **Tracking Service**: Processes GPS data and triggers alerts.
- **Notification Service**: Sends notifications to caregivers.
- **User Service**: Handles user registration and authentication.

### CI/CD Pipeline

The CI/CD pipeline automates the process of building, testing, and deploying the application. Each time code is pushed to the repository, Jenkins triggers the pipeline:

1. **Code Checkout**: Fetch the latest code from the repository.
2. **Build**: Use Maven to build the backend services.
3. **Test**: Run unit tests and integrate with SonarQube for code quality checks.
4. **Docker Image Build**: Create Docker images for the services.
5. **Deploy**: Deploy the application using Kubernetes and Docker Compose.

### Cloud Architecture

The system is hosted on a cloud infrastructure using Kubernetes for container orchestration. It employs a Virtual Private Cloud (VPC) with subnets for different services (e.g., Kubernetes nodes, Jenkins server). The cloud setup ensures high availability and scalability.

## Conclusion

The **Cognitive Finder** project provides a comprehensive solution for managing patients with cognitive disorders. By leveraging wearable sensors, real-time monitoring, and a robust cloud infrastructure, it enhances patient safety and caregiver efficiency. The project is built using modern technologies like microservices, Docker, Kubernetes, and CI/CD practices, ensuring scalability and maintainability for future enhancements.

For any questions or contributions, please feel free to contact us via email at `devolpex@gmail.com`.