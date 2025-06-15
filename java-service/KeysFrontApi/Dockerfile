FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/KeysFrontApi-0.0.1.jar /app/keys-front.jar

ENTRYPOINT ["java", "-jar", "keys-front.jar"]