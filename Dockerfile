FROM openjdk:17
EXPOSE 8080
ADD target/PBL-docker.jar PBL-docker.jar
ENTRYPOINT ["java", "-jar ", "/PBL-docker.jar"]