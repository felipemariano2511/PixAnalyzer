FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/ReceitaFederalCNPJ-0.0.1.jar /app/receita-federal-cnpj-api.jar
ENTRYPOINT ["java", "-jar", "receita-federal-cnpj-api.jar"]