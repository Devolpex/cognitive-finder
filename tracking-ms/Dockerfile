FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Define build arguments
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD
ARG CONSUL_HOST
ARG CONSUL_PORT
ARG TRACCAR_API
ARG TRACCAR_USER
ARG TRACCAR_PASSWORD

# Set environment variables from build arguments
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV CONSUL_HOST=${CONSUL_HOST}
ENV CONSUL_PORT=${CONSUL_PORT}
ENV TRACCAR_API=${TRACCAR_API}
ENV TRACCAR_USER=${TRACCAR_USER}
ENV TRACCAR_PASSWORD=${TRACCAR_PASSWORD}


# Copy the JAR file from the build stage
# COPY --from=build /app/target/*.jar app.jar
COPY  target/*.jar app.jar
# Expose the port
EXPOSE 8883

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
