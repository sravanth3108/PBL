# Build stage
FROM maven:3.8-openjdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Pprod -DskipTests

# Runtime stage
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/Project-0.0.1-SNAPSHOT.jar PBL.jar
CMD ["java", "-jar", "PBL.jar"]
