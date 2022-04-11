FROM openjdk:11.0.13-jre-slim-buster

ENV TZ='GMT-3'

VOLUME /tmp

EXPOSE 8083

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} CaeAgendamento.jar

ENTRYPOINT ["java","-Xmx512M","-jar","/CaeAgendamento.jar"]