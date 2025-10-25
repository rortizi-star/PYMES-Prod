# Multi-stage Dockerfile for building and running the Spring Boot backend
# - Builder stage uses Maven to produce the runnable jar
# - Final stage uses a lightweight JRE image to run the jar

#########################
# Builder
#########################
FROM maven:3.9-jdk-21 AS builder
WORKDIR /workspace

# Copy maven wrapper and pom to take advantage of layer caching for dependencies
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Download dependencies (offline step) to speed up subsequent builds
RUN mvn -B -f pom.xml dependency:go-offline

# Copy sources and build the application
COPY src ./src
RUN mvn -B -f pom.xml -DskipTests package

#########################
# Runtime
#########################
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the application jar produced by the builder stage
COPY --from=builder /workspace/target/*.jar /app/app.jar

# Port: Render provides a PORT environment variable at runtime.
# We expose 8082 as a default for local testing, but the JVM will bind to $PORT if provided.
EXPOSE 8082

# Default PORT environment variable (used only if Render doesn't provide one locally)
ENV PORT=8082

# Use shell form so environment variable expansion works for -Dserver.port
ENTRYPOINT ["sh","-c","java -Dserver.port=${PORT:-8082} -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]
