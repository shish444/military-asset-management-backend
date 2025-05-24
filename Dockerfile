# Build stage
FROM maven:3.8.7-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Environment variables
ENV PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "app.jar"]