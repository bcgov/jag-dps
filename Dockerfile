##############################################################################################
#### Stage where the git submodules are updated                                            ###
##############################################################################################
## Defining Arguments and env vars
ARG DPS_SERVICE_NAME

FROM alpine/git as libraries
WORKDIR /libs
COPY . .
RUN git submodule update --init

#############################################################################################
###              Stage where Docker is caching the dependencies spring boot app using maven               ###
#############################################################################################
#FROM image-registry.apps.silver.devops.gov.bc.ca/043918-tools/maven:3.8.6-eclipse-temurin-8 as dependencies
FROM maven:3.8.6-eclipse-temurin-17 as dependencies

## Defining Arguments and env vars
ARG DPS_SERVICE_NAME

## Definig home folder
ENV HOME=/opt/app
RUN mkdir -p $HOME
WORKDIR $HOME

## Adding pom files
COPY src/pom.xml pom.xml

COPY src/dps-email-poller/pom.xml dps-email-poller/pom.xml
COPY src/dps-email-worker/pom.xml dps-email-worker/pom.xml
COPY src/dps-notification-service/pom.xml dps-notification-service/pom.xml
COPY src/dps-payment-service/pom.xml dps-payment-service/pom.xml
COPY src/dps-validation-service/pom.xml dps-validation-service/pom.xml

COPY src/figaro-validation-service/pom.xml figaro-validation-service/pom.xml

COPY src/spd-notification-worker/pom.xml spd-notification-worker/pom.xml
COPY src/vips-notification-worker/pom.xml vips-notification-worker/pom.xml
COPY src/report/pom.xml report/pom.xml

COPY --from=libraries /libs/src/libs/jag-vips-client/src/jag-vips-client/pom.xml libs/jag-vips-client/src/jag-vips-client/pom.xml

COPY src/libs/dps-bom/pom.xml libs/dps-bom/pom.xml
COPY src/libs/dfcms-ords-client/pom.xml libs/dfcms-ords-client/pom.xml
COPY src/libs/dps-cache-starter/pom.xml libs/dps-cache-starter/pom.xml
COPY src/libs/dps-email-client/pom.xml libs/dps-email-client/pom.xml
COPY src/libs/dps-messaging-starter/pom.xml libs/dps-messaging-starter/pom.xml
COPY src/libs/dps-notification/pom.xml libs/dps-notification/pom.xml
COPY src/libs/figaro-ords-client/pom.xml libs/figaro-ords-client/pom.xml
COPY src/libs/dps-commons/pom.xml libs/dps-commons/pom.xml
COPY src/libs/dps-files/pom.xml libs/dps-files/pom.xml
COPY src/libs/dps-monitoring/pom.xml libs/dps-monitoring/pom.xml
COPY src/libs/dps-sftp-starter/pom.xml libs/dps-sftp-starter/pom.xml

RUN mvn dependency:go-offline \
    -Pall \
    -DskipTests \
    --no-transfer-progress \
    --batch-mode \
    --fail-never

#############################################################################################
###              Stage where Docker is building spring boot app using maven               ###
#############################################################################################
FROM dependencies as build

ARG DPS_SERVICE_NAME
ARG MVN_PROFILES=${DPS_SERVICE_NAME}
ARG SKIP_TESTS=true

ENV HOME=/opt/app
RUN mkdir -p $HOME
WORKDIR $HOME

COPY src .

COPY --from=libraries /libs/src/libs/jag-vips-client/src/jag-vips-client libs/jag-vips-client/src/jag-vips-client

RUN mvn -B clean install \
    --no-transfer-progress \
    --batch-mode \
    -DskipTests=${SKIP_TESTS} \
    -P libs

RUN mvn clean package \
    --no-transfer-progress \
    --batch-mode \
    -DskipTests=${SKIP_TESTS} \
    -P ${MVN_PROFILES}

##############################################################################################
#### Stage where Docker is running a java process to run a service built in previous stage ###
##############################################################################################
#FROM image-registry.apps.silver.devops.gov.bc.ca/043918-tools/eclipse-temurin:8-jre-jammy
FROM eclipse-temurin:17-jre-jammy

ARG DPS_SERVICE_NAME

ENV HOME=/opt/app
RUN mkdir -p $HOME
WORKDIR $HOME

COPY --from=build ${HOME}/${DPS_SERVICE_NAME}/target/${DPS_SERVICE_NAME}-*.jar ${HOME}/service.jar

CMD ["java", "-jar", "service.jar"]
#############################################################################################