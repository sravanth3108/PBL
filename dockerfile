# Build stage
FROM maven:3.9-openjdk-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Pprod -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Project-0.0.1-SNAPSHOT.jar PBL.jar
CMD ["java", "-jar", "PBL.jar"]
