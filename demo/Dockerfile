# Multi-stage Dockerfile for building and running the Spring Boot backend
# - Builder stage uses Maven to produce the runnable jar
# - Final stage uses a lightweight JRE image to run the jar

#########################
# Builder
#########################
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /workspace

# Install Maven in the builder image (works even if specific maven:<tag> isn't available)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copy pom to take advantage of layer caching for dependencies
# (do not assume .mvn or mvnw exist in the context)
COPY pom.xml ./

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

# Copiar entrypoint que configura NODE_NAME y ejecuta la JVM
COPY docker-entrypoint.sh /app/docker-entrypoint.sh
RUN chmod +x /app/docker-entrypoint.sh

# Instalar curl para los healthchecks en el contenedor runtime (pequeña dependencia para demo)
RUN apt-get update && apt-get install -y curl ca-certificates && rm -rf /var/lib/apt/lists/*

# Port: Render provides a PORT environment variable at runtime.
# We expose 8082 as a default for local testing, but the JVM will bind to $PORT if provided.
EXPOSE 8082

# Default PORT environment variable (used only if Render doesn't provide one locally)
ENV PORT=8082

# Usar el entrypoint para inicializar NODE_NAME y lanzar la aplicación
ENTRYPOINT ["/app/docker-entrypoint.sh"]
