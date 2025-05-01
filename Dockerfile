# Base image with Java
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy Maven build output into the image
COPY target/user-management-api-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
