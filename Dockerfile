##### Dockerfile #####
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR ./src
COPY . .

RUN mvn clean install

FROM openjdk:21-jdk

COPY --from=build src/target/employee-0.0.1-SNAPSHOT.jar /run/employee-0.0.1-SNAPSHOT.jar

EXPOSE 10000

ENV JAVA_OPTIONS="-Xmx2048m -Xms256m"
ENTRYPOINT java -jar $JAVA_OPTIONS /run/employee-0.0.1-SNAPSHOT.jar