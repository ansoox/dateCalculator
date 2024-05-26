FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY src src

RUN mvn clean install -DskipTests

FROM openjdk:21
WORKDIR /app

COPY --from=build /app/target ./target

EXPOSE 8080

CMD ["java", "-jar", "target/dateCalculator-0.0.1-SNAPSHOT.jar"]