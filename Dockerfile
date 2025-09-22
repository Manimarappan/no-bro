# Multi-stage build: First stage builds the JAR with JDK 21, second runs it
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Production stage with JRE 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080  # Or your app's port (default for Spring Boot is 8080)
ENTRYPOINT ["java", "-jar", "app.jar"]