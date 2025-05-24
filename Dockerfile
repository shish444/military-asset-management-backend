# Use OpenJDK 21 as base image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built jar into the image (update the filename if needed)
COPY target/*.jar app.jar

# Expose the port (Render sets this dynamically)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
