# Part 1:

FROM ubuntu:latest as build

# Install unzip
RUN apt-get update \
  && apt-get upgrade -y \
  && apt-get install -y unzip \
  && rm -rf /var/lib/apt/lists/*

COPY target/*.war ./phax.war

RUN unzip phax.war -d /phax

# Part 2:
    
FROM tomcat:10-jdk11

ENV CATALINA_OPTS="$CATALINA_OPTS -Djava.security.egd=file:/dev/urandom"

WORKDIR $CATALINA_HOME/webapps
RUN rm -rf manager host-manager docs examples ROOT

COPY --from=build /phax $CATALINA_HOME/webapps/ROOT
