# Use an official JDK 21 runtime as base image
FROM openjdk:21-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy the jar file into the container
COPY target/*.jar app.jar

# Expose port (default = 8080 in Spring Boot)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
