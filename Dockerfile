# ESTÁGIO 1: Compilação (Build)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ESTÁGIO 2: Execução (Runtime)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 (O Render vai mapear isso automaticamente)
EXPOSE 8080

# Comando de inicialização passando a porta dinâmica do Render
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-Xmx512m", "-jar", "app.jar"]