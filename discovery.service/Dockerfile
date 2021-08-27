FROM maven:3.8.1-adoptopenjdk-11-openj9 AS builder

COPY pom.xml .

RUN mvn -e -B dependency:resolve

COPY /src ./src

RUN mvn -e -B package

FROM adoptopenjdk:11-jdk-openj9

COPY --from=builder target/discovery.service.jar /

CMD ["java", "-jar", "/discovery.service.jar"]
