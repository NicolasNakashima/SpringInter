FROM maven:3.8.3-openjdk-17 as Build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=Build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]