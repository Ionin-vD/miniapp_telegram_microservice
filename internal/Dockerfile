FROM openjdk:21-slim

WORKDIR /app
COPY target/internal-v1-beta.jar ./internal-v1-beta.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "internal-v1-beta.jar"]
