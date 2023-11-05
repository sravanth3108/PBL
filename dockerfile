# Build stage
FROM maven:3.8-openjdk-18 AS build
WORKDIR /app  # Set the working directory inside the container
COPY . .       # Copy the project files into the container's working directory
RUN mvn clean package -Pprod -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app  # Set the working directory inside the container
COPY --from=build /app/target/DogsManagementSystem-0.0.1-SNAPSHOT.jar DogsManagementSystem.jar
CMD ["java", "-jar", "DogsManagementSystem.jar"]
