# DPS

A Document Processing System.

## Project Structure

    .
    ├── .github                     # Contains GitHub Related sources
    ├── configurations              # 
    ├── openshift                   # openshift templates and pipeline
    ├── src/                        # application source files
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


## Payment Service

See [Doc](src/paymentservice/README.md).

### Install

Run

```bash
mvn install -P paymentservice
```

## DPS Validation Service

### Install

Run

```bash
mvn install -P dpsvalidationservice
```

## Figaro Validation Service

### Install

Run

```bash
mvn install -P figvalidationservice
```

## Endpoints

| URL | Method | Description |
| --- | --- | --- |
| **Payment Service** | --- | --- |
| [http://localhost:8080/paymentservice/bamboraconfiguration](http://localhost:8080/paymentservice/bamboraconfiguration) | GET | Bambora configuration url |
| [http://localhost:8080/paymentservice/getSinglePaymentURL](http://localhost:8080/paymentservice/getSinglePaymentURL) | GET | Single Payment Url |
| [http://localhost:8081/paymentservice/actuator/health](http://localhost:8080/paymentservice/actuator/health) | GET | Payment Service Health |
| [http://localhost:8081/paymentservice/swagger-ui.html](http://localhost:8080/paymentservice/swagger-ui.html) | GET | Figaro Validator Swagger-UI |
| [http://localhost:8081/paymentservice/v2/api-docs](http://localhost:8080/paymentservice/swagger-ui.html) | GET | Figaro Validator Swagger-UI |
| **Figaro Validation** | --- | --- |
| [http://localhost:8080/figvalidationservice/locateMatchingApplicants](http://localhost:8080/figvalidationservice/locateMatchingApplicants) | GET | Locate Matching Applicants |
| [http://localhost:8080/figvalidationservice/validateApplicantService](http://localhost:8080/figvalidationservice/validateApplicantService) | GET | Validate Applicant Service |
| [http://localhost:8080/figvalidationservice/validateApplicantForSharing](http://localhost:8080/figvalidationservice/validateApplicantForSharing) | GET | Validate Applicant Sharing |
| [http://localhost:8080/figvalidationservice/validateApplicantPartyId](http://localhost:8080/figvalidationservice/validateApplicantPartyId) | GET | Validate Applicant Party ID |
| [http://localhost:8082/figvalidationservice/actuator/health](http://localhost:8082/figvalidationservice/actuator/health) | GET | Figaro Validator Health | 
| [http://localhost:8082/figvalidationservice/swagger-ui.html](http://localhost:8082/figvalidationservice/swagger-ui.html) | GET | Figaro Validator Swagger-UI |
| **DPS Validation**  | --- | --- |
| [http://localhost:8080/dpsvalidationservice/getValidOpenDFCMCase](http://localhost:8080/dpsvalidationservice/getValidOpenDFCMCase) | GET | Valid Open DFCM Case |
| [http://localhost:8083/dpsvalidationservice/actuator/health](http://localhost:8083/dpsvalidationservice/actuator/health) | GET | DPS Validation Service Health | 
| [http://localhost:8083/dpsvalidationservice/swagger-ui.html](http://localhost:8083/dpsvalidationservice/swagger-ui.html) | GET | DPS Validation Service Swagger-UI |
| [http://localhost:8083/dpsvalidationservice/v2/api-docs](http://localhost:8083/dpsvalidationservice/v2/api-docs) | GET | DPS Validation Service Swagger |

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
