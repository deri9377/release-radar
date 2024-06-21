# Use AdoptOpenJDK 17 as base image
FROM openjdk:17-alpine as builder

# Set working directory inside the container
WORKDIR /app

# Copy Gradle files
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .

# Copy project files
COPY src ./src

# Build application
RUN ./gradlew clean build

# Second stage to create minimal runtime image
FROM openjdk:17-alpine

# Set working directory inside the container
WORKDIR /app

# Copy JAR file from the builder stage
COPY --from=builder /app/build/libs/release-radar-0.0.1-SNAPSHOT.jar ./app.jar

# Expose port 8080
EXPOSE 8000

# Command to run the application
CMD ["java", "-jar", "app.jar"]