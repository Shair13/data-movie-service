FROM openjdk:17-alpine

RUN apk update && apk upgrade && \
    apk add \
    maven

RUN mkdir /code

COPY . /code

RUN cd /code && \
    mvn package && \
    mkdir /opt/app && \
    mv /code/target/data-service-0.0.1-SNAPSHOT.jar /opt/app && \
    apk del maven && \
    rm -r /code

EXPOSE 8081

WORKDIR /opt/app

CMD java -jar data-service-0.0.1-SNAPSHOT.jar