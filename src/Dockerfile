#############################################################################################
###              Stage where Docker is caching the dependencies spring boot app using maven               ###
#############################################################################################
FROM image-registry.apps.silver.devops.gov.bc.ca/043918-tools/maven:3.8.6-eclipse-temurin-8 as dependencies

## Defining Arguments and env vars
ARG NEXUS_URL=https://nexus-043918-tools.apps.silver.devops.gov.bc.ca
ARG DPS_SERVICE_NAME

ENV DPS_SERVICE_NAME=${DPS_SERVICE_NAME}
ENV NEXUS_URL=${NEXUS_URL}

## Definig home folder
ENV HOME=/opt/app
RUN mkdir -p $HOME
WORKDIR $HOME

## Adding pom files
RUN pwd
RUN ls -la

COPY pom.xml pom.xml

COPY ./dps-email-poller/pom.xml dps-email-poller/pom.xml
COPY ./dps-email-worker/pom.xml dps-email-worker/pom.xml
COPY ./dps-notification-service/pom.xml dps-notification-service/pom.xml
COPY ./dps-payment-service/pom.xml dps-payment-service/pom.xml
COPY ./dps-registration-api/pom.xml dps-registration-api/pom.xml
COPY ./dps-validation-service/pom.xml dps-validation-service/pom.xml

COPY ./figaro-validation-service/pom.xml figaro-validation-service/pom.xml

COPY ./spd-notification-worker/pom.xml spd-notification-worker/pom.xml
COPY ./vips-notification-worker/pom.xml vips-notification-worker/pom.xml

COPY ./libs/dps-bom/pom.xml libs/dps-bom/pom.xml
COPY ./libs/dfcms-ords-client/pom.xml libs/dfcms-ords-client/pom.xml
COPY ./libs/dps-cache-starter/pom.xml libs/dps-cache-starter/pom.xml
COPY ./libs/dps-email-client/pom.xml libs/dps-email-client/pom.xml
COPY ./libs/dps-messaging-starter/pom.xml libs/dps-messaging-starter/pom.xml
COPY ./libs/dps-notification/pom.xml libs/dps-notification/pom.xml
COPY ./libs/figaro-ords-client/pom.xml libs/figaro-ords-client/pom.xml
COPY ./libs/dps-commons/pom.xml libs/dps-commons/pom.xml
COPY ./libs/dps-files/pom.xml libs/dps-files/pom.xml
COPY ./libs/dps-monitoring/pom.xml libs/dps-monitoring/pom.xml
COPY ./libs/dps-sftp-starter/pom.xml libs/dps-sftp-starter/pom.xml
COPY ./libs/otssoa-ords-client/pom.xml libs/otssoa-ords-client/pom.xml

RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline \
    -Pall \
    -DskipTests \
    --no-transfer-progress \
    --batch-mode \
    --fail-never


#############################################################################################
###              Stage where Docker is building spring boot app using maven               ###
#############################################################################################
FROM dependencies as build

ARG NEXUS_URL=https://nexus-043918-tools.apps.silver.devops.gov.bc.ca

ARG DPS_SERVICE_NAME

ENV DPS_SERVICE_NAME=${DPS_SERVICE_NAME}
ENV NEXUS_URL=${NEXUS_URL}

ENV HOME=/opt/app
RUN mkdir -p $HOME
WORKDIR $HOME

COPY . .

RUN --mount=type=cache,target=/root/.m2 mvn clean package \
    --no-transfer-progress \
    --batch-mode \
    -DskipTests \
    -P ${DPS_SERVICE_NAME}


##############################################################################################
#### Stage where Docker is running a java process to run a service built in previous stage ###
##############################################################################################
FROM image-registry.apps.silver.devops.gov.bc.ca/043918-tools/eclipse-temurin:8-jre-jammy

ARG DPS_SERVICE_NAME
ENV DPS_SERVICE_NAME=${DPS_SERVICE_NAME}

ENV HOME=/opt/app
RUN mkdir -p $HOME
WORKDIR $HOME

COPY --from=build ${HOME}/${DPS_SERVICE_NAME}/target/${DPS_SERVICE_NAME}-*.jar ${HOME}/service.jar

CMD ["java", "-jar", "${HOME}/service.jar"]
#############################################################################################
