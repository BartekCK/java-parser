FROM maven:3.6.0-jdk-11-slim AS build
COPY src src
COPY pom.xml .
RUN mvn -f pom.xml clean package

FROM openjdk:11
COPY --from=build target/backend-converter.jar backend-converter.jar
ENTRYPOINT ["java", "-jar", "backend-converter.jar"]
