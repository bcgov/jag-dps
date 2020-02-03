# DPS

A Document Processing System.

## Project Structure

    .
    ├── .github                     # Contains GitHub Related sources
    ├── configurations              # 
    ├── openshift                   # openshift templates and pipeline
    ├── src/                        # application source files
    │   ├── spdnotificationworker/     # SPD Notification Worker
    │   ├── dpsnotificationservice/ # DPS Notification service  
    │   ├── dpsvalidationservice/   # DPS DFCM service
    |   ├── figvalidationservice/   # DPS Figaro Validator service
    |   ├── paymentservice/         # DPS payment service
    │   └── service-api             # DPS service api
    ├── tests                       # Tests files
    ├── docker-compose.yml          # docker compose definition
    ├── LICENSE                     # Apache License
    └── README.md                   # This file.

## Run

Install [Docker](https://www.docker.com/)

Create a .env file based of `.env.template` and choose a password for splunk

run

```bash
docker-compose up dps-splunk
```

[login](http://localhost:8000) into splunk `admin:<your password>`

Enable splunk [Http Event Collector](https://docs.splunk.com/Documentation/Splunk/7.2.3/Data/UsetheHTTPEventCollector) and create a token

Update your .env file with the newly created token value and the figaro server information

Install [Splunk Addon for NGINX](https://splunkbase.splunk.com/app/3258/) app following this [instructions](https://docs.splunk.com/Documentation/AddOns/released/Overview/Singleserverinstall)

Restart splunk when prompted

Restart docker-compose with the new changes

```bash
docker-compose up --build -d
```

## Dps Service List

| Name | Doc | Notes |
| --- | --- | --- |
| Payment Service | [Doc](src/paymentservice/README.md) | |
| CRRP Notification Worker| [Doc](src/crrp-notification-worker/README.md) | |
| DPS Notification Service| [Doc](src/dpsnotificationservice/README.md) | |
| DPS Validation Service | [Doc](src/dpsvalidationservice/README.md) | |
| Figaro Validation Service | [Doc](src/figvalidationservice/README.md) | |
| VIPS Notification Worker| [Doc](src/vips-notification-worker/README.md) | |

## Endpoints

| URL | Method | Description |
| --- | --- | --- |
| **Payment Service** | --- | --- |
| [http://localhost:5050/paymentservice/bamboraconfiguration](http://localhost:5050/paymentservice/bamboraconfiguration) | GET | Bambora configuration url |
| [http://localhost:5050/paymentservice/getSinglePaymentURL](http://localhost:5050/paymentservice/getSinglePaymentURL) | GET | Single Payment Url |
| [http://localhost:8081/paymentservice/actuator/health](http://localhost:8081/paymentservice/actuator/health) | GET | Payment Service Health |
| [http://localhost:8081/paymentservice/swagger-ui.html](http://localhost:8081/paymentservice/swagger-ui.html) | GET | Figaro Validator Swagger-UI |
| [http://localhost:8081/paymentservice/v2/api-docs](http://localhost:8081/paymentservice/v2/api-docs) | GET | Figaro Validator Api docs |
| **Figaro Validation** | --- | --- |
| [http://localhost:5050/figvalidationservice/locateMatchingApplicants](http://localhost:5050/figvalidationservice/locateMatchingApplicants) | GET | Locate Matching Applicants |
| [http://localhost:5050/figvalidationservice/validateApplicantService](http://localhost:5050/figvalidationservice/validateApplicantService) | GET | Validate Applicant Service |
| [http://localhost:5050/figvalidationservice/validateApplicantForSharing](http://localhost:5050/figvalidationservice/validateApplicantForSharing) | GET | Validate Applicant Sharing |
| [http://localhost:5050/figvalidationservice/validateApplicantPartyId](http://localhost:5050/figvalidationservice/validateApplicantPartyId) | GET | Validate Applicant Party ID |
| [http://localhost:8082/figvalidationservice/actuator/health](http://localhost:8082/figvalidationservice/actuator/health) | GET | Figaro Validator Health | 
| [http://localhost:8082/figvalidationservice/swagger-ui.html](http://localhost:8082/figvalidationservice/swagger-ui.html) | GET | Figaro Validator Swagger-UI |
| **DPS Validation**  | --- | --- |
| [http://localhost:5050/dpsvalidationservice/getValidOpenDFCMCase](http://localhost:5050/dpsvalidationservice/getValidOpenDFCMCase) | GET | Valid Open DFCM Case |
| [http://localhost:8083/dpsvalidationservice/actuator/health](http://localhost:8083/dpsvalidationservice/actuator/health) | GET | DPS Validation Service Health | 
| [http://localhost:8083/dpsvalidationservice/swagger-ui.html](http://localhost:8083/dpsvalidationservice/swagger-ui.html) | GET | DPS Validation Service Swagger-UI |
| [http://localhost:8083/dpsvalidationservice/v2/api-docs](http://localhost:8083/dpsvalidationservice/v2/api-docs) | GET | DPS Validation Service Swagger |
| **DPS Output Notification** | --- | --- |
| [http://localhost:5054/dpsnotificationservice/actuator/health](http://localhost:5054/dpsnotificationservice/actuator/health) | GET | DPS Notification Service Health |
| [http://localhost:5050/ws/dpsOutputNotification.wsdl](http://localhost:5050/ws/dpsOutputNotification.wsdl) | GET | DPS Output Notification Service WSDL |
| **CRRP Notification Worker** | --- | --- |
| [http://localhost:5055/crrpnotificationworker/actuator/health](http://localhost:5054/crrpnotificationworker/actuator/health) | GET | CRRP Notification 
| **CRRP Notification Worker** | --- | --- |
| [http://localhost:5056/vipsnotificationworker/actuator/health](http://localhost:5056/vipsnotificationworker/actuator/health) | GET | CRRP Notification 


Worker Health |
| **SUPPORT APPS** | --- | --- |
| [RabbitMq](http://localhost:15672) | Rabbit MQ management console | --- |
| [Splunk](http://localhost:8000) | Splunk Web | --- |

# Tests

## Postman

We maintain a [postman collection](tests/postman/DPS-Service-Api.postman_collection.json) and a [postman environment](tests/postman/dps-env.postman_environment.json).

You can also run the collection using [newman](https://www.npmjs.com/package/newman)

Install newman as a global tool

```bash
npm install -g newman
```

Run the collection

```bash
cd tests/postman
newman run DPS-Service-Api.postman_collection.json -e dps-env.postman_environment.json
```
