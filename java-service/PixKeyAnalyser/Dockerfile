FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/PixAnalyser-0.0.1.jar /app/pix-analyser-api.jar

ENTRYPOINT ["java", "-jar", "pix-analyser-api.jar"]