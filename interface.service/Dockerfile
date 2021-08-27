FROM maven:3.8.1-adoptopenjdk-11-openj9 AS builder

COPY pom.xml .

RUN mvn -e -B dependency:resolve

COPY /src ./src

RUN mvn -e -B package

FROM adoptopenjdk:11-jdk-openj9

COPY --from=builder target/interface.service.war /

CMD ["java", "-jar", "/interface.service.war"]
#CMD echo here; sleep 30; echo there; java -jar /interface.service.war

#FROM tomcat:10.0.10-jdk11-openjdk-buster
#COPY --from=builder target/interface.service.war /usr/local/tomcat/webapps/
#CMD chmod +x /usr/local/tomcat/bin/catalina.sh
#CMD ["catalina.sh", "run"]
