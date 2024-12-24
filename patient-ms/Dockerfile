FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Define build arguments
ARG MONGODB_URI
ARG CONSUL_HOST
ARG CONSUL_PORT


# Set environment variables from build arguments
ENV MONGODB_URI=${MONGODB_URI}
ENV CONSUL_HOST=${CONSUL_HOST}
ENV CONSUL_PORT=${CONSUL_PORT}



# Copy the JAR file from the build stage
# COPY --from=build /app/target/*.jar app.jar
COPY  target/*.jar app.jar
# Expose the port
EXPOSE 8882

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
