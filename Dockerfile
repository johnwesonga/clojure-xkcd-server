FROM openjdk:8u181-alpine3.8

WORKDIR /

COPY xkcd-server.jar xkcd-server.jar
COPY resources resources
EXPOSE 8080

CMD java -jar xkcd-server.jar