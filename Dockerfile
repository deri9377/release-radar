# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the build artifacts from the build stage
COPY build/libs/*.jar /app/release-radar.jar

# Expose the port your application runs on
EXPOSE 8000

# Command to run the application
CMD ["java", "-jar", "release-radar.jar"]
