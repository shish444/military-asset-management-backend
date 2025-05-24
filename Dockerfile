# Build stage with valid Maven+JDK 21 tag
FROM maven:3.9-eclipse-temurin-21-jdk AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage with verified JRE 21 image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Environment variables
ENV PORT=8080
EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "app.jar"]
