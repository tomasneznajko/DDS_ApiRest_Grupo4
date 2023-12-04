# syntax = docker/dockerfile:1.2
#
# Build stage
#
FROM maven:3.8.6-openjdk-18 AS build
COPY . .
RUN mvn clean package assembly:single -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build target/Tutorial_Definitivo_Javalin-1.0-SNAPSHOT-jar-with-dependencies.jar Tutorial_Definitivo_Javalin.jar
# ENV PORT=8082
EXPOSE 8082
CMD ["java","-classpath","Tutorial_Definitivo_Javalin.jar","FusionadorDeComunidades.ApiRest"]
