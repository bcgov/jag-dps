#############################################################################################
###                Stage where Docker is pulling all maven dependencies                   ###
#############################################################################################
FROM maven:3.6.3-jdk-8 as dependencies

ARG PROXY_SET=false
ARG PROXY_HOST=
ARG PROXY_PORT=

COPY pom.xml ./

RUN mvn -B dependency:go-offline \
        -DproxySet=${PROXY_SET} \
        -DproxyHost=${PROXY_HOST} \
        -DproxyPort=${PROXY_PORT}
#############################################################################################

#############################################################################################
###              Stage where Docker is building spring boot app using maven               ###
#############################################################################################
FROM dependencies as build

ARG PROXY_SET=false
ARG PROXY_HOST=
ARG PROXY_PORT=

COPY src ./src

RUN mvn -B clean package \
        -DproxySet=${PROXY_SET} \
        -DproxyHost=${PROXY_HOST} \
        -DproxyPort=${PROXY_PORT}

#############################################################################################

#############################################################################################
### Stage where Docker is running a java process to run a service built in previous stage ###
#############################################################################################
FROM openjdk:8-jdk-slim

COPY --from=build /target/paymentservice-*.jar /app/service.jar

CMD ["java", "-jar", "/app/service.jar"]
#############################################################################################
