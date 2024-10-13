# Build the application
FROM maven:3-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Create the runtime image
FROM openjdk:17-ea-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
