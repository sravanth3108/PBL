FROM maven:3.8-openjdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

 

FROM openjdk:17-jdk-slim
COPY --from=build /target/ProfessionalBasedLearnin-0.0.1-SNAPSHOT.jar PBL.jar
CMD ["java", "-jar", "PBL.jar"]