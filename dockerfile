dockerfileFROM openjdk:21
LABEL maintainer ="howtodoinjava"
ADD target/Project-0.0.1-SNAPSHOT.jar springDocker-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","springDocker-0.0.1-SNAPSHOT.jar"]