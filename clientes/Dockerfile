FROM eclipse-temurin:21-jdk-alpine

COPY target/*.jar app.jar

COPY src/main/resources/clientes_enderecos.csv src/main/resources/clientes_enderecos.csv

CMD ["java", "-jar", "/app.jar"]