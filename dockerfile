FROM maven:3.8-openjdk-21 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

 

FROM openjdk:21-jdk-slim
COPY --from=build /target/Project-0.0.1-SNAPSHOT.jar PBL.jar
CMD ["java", "-jar", "PBL.jar"]