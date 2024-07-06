FROM amazoncorretto:11-alpine-jdk
LABEL authors="sarthak"
MAINTAINER baeldung.com
COPY target/hamro_barber-0.0.1-SNAPSHOT.jar hamro_barber-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/hamro_barber-0.0.1-SNAPSHOT.jar"]