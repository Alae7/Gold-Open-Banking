FROM openjdk:17

WORKDIR /app

COPY target/*.jar  discovery-service.jar

ENTRYPOINT ["java","-jar","discovery-service.jar"]
