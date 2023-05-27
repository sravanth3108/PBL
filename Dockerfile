FROM maven:3.8-openjdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

 

FROM openjdk:17-jdk-slim
COPY --from=build /target/PBL-docker.jar DogsManagementSystem.jar
CMD ["java", "-jar", "PBL-docker.jar"]