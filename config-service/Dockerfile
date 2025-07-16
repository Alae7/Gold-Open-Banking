FROM openjdk:17

WORKDIR /app

COPY target/*.jar  config-service.jar

ENTRYPOINT ["java","-jar","config-service.jar"]
