# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the build artifacts from the build stage
COPY build/libs/release-radar-0.0.1-SNAPSHOT.jar /app/release-radar-0.0.1-SNAPSHOT.jar
COPY 

# Expose the port your application runs on
EXPOSE 8000

# Command to run the application
CMD ["java", "-jar", "release-radar-0.0.1-SNAPSHOT.jar"]
