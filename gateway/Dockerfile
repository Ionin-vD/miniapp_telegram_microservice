FROM openjdk:21-slim

WORKDIR /app
COPY target/gateway-v1-beta.jar gateway-v1-beta.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "gateway-v1-beta.jar"]