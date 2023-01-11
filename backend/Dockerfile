FROM maven:3.8.6-openjdk-11-slim AS build

RUN mkdir -p /app/backend

WORKDIR /app/backend

COPY . .

RUN mv src/main/resources/application-dev.docker.yaml src/main/resources/application-dev.yaml
RUN mv src/main/resources/application-test.docker.yaml src/main/resources/application-test.yaml

RUN mvn clean package -Dmaven.test.skip

FROM openjdk:11-jre-slim

EXPOSE 8080

COPY --from=build /app/backend/target/materials-0.0.1-SNAPSHOT.jar /app/backend/entry.jar

ENTRYPOINT ["java","-jar","/app/backend/entry.jar"]
