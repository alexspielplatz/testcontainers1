# OpenLiberty
#FROM openliberty/open-liberty:full-java8-openj9-ubi
#COPY config /config/
#ADD war/myservice.war /config/dropins
#ARG VERBOSE=true
#RUN configure.sh
# Wildfly
#FROM jboss/wildfly
#ADD build/libs/myservice.war /opt/jboss/wildfly/standalone/deployments/

# Payara
FROM payara/server-full
COPY war/myservice.war $DEPLOY_DIR

# TomEE (not working yet)
#FROM tomee:8-jre-8.0.0-M2-microprofile
#COPY build/libs/myservice.war /usr/local/tomee/webapps/
