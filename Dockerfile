# Build stage with JDK 21 (even though Maven uses JDK 18)
FROM maven:3.8.7-openjdk-18-slim AS builder

# Install JDK 21 manually
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://download.java.net/java/GA/jdk21/fd2272bbf8e04c3dbaee13770090416c/35/GPL/openjdk-21_linux-x64_bin.tar.gz && \
    tar -xzf openjdk-21_linux-x64_bin.tar.gz -C /opt && \
    rm openjdk-21_linux-x64_bin.tar.gz

# Set JAVA_HOME to JDK 21
ENV JAVA_HOME=/opt/jdk-21
ENV PATH="$JAVA_HOME/bin:$PATH"

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage with JDK 21
FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENV PORT=8080
EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "app.jar"]
