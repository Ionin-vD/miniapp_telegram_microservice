FROM openjdk:21-slim

WORKDIR /app
COPY target/api-v1-beta.jar ./api-v1-beta.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-v1-beta.jar"]
