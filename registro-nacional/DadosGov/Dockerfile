FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/DadosGov-0.0.1.jar /app/dados-gov-api.jar
ENTRYPOINT ["java", "-jar", "dados-gov-api.jar"]