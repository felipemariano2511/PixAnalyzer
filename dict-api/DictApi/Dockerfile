FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/DictApi-0.0.1.jar /app/dict-api.jar

ENTRYPOINT ["java", "-jar", "dict-api.jar"]