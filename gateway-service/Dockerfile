FROM openjdk:17

WORKDIR /app

COPY target/*.jar  gateway-service.jar

ENTRYPOINT ["java","-jar","gateway-service.jar"]
