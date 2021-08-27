FROM maven:3.8.1-adoptopenjdk-11-openj9 AS builder

COPY pom.xml .

RUN mvn -e -B dependency:resolve

COPY /src ./src

RUN mvn -e -B package

FROM adoptopenjdk:11-jdk-openj9

COPY --from=builder target/stock.service.jar /

COPY ./wait-for-it.sh ./wait-for-it.sh
RUN chmod +x wait-for-it.sh

COPY --from=builder target/stock.service.jar /

ENTRYPOINT [ "./wait-for-it.sh", "discovery-service-d:8761", "--strict", "--timeout=150", "--", "java", "-jar", "/stock.service.jar" ]
