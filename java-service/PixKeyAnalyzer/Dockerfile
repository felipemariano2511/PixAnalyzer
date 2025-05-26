FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/PixAnalyzer-0.0.1.jar /app/pix-analyzer-api.jar

ENTRYPOINT ["java", "-jar", "pix-analyzer-api.jar"]