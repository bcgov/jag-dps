# DPS

A Document Processing System.

## Project Structure

    .
    ├── .github                 # Contains GitHub Related sources
    ├── src/                    # application source files
    │   ├── paymentservice/     # DPS payment service
    │   └── service-api         # DPS service api
    ├── docker-compose.yml      # docker compose definition
    ├── LICENSE                 # Apache License
    └── README.md               # This file.

## Run

Install [Docker](https://www.docker.com/)

Create a .env file based of `.env.template`

run

```bash
docker-compose up
```

[login](http://localhost:8000) into splunk `admin:admin123!`

Enable splunk [Http Event Collector](https://docs.splunk.com/Documentation/Splunk/7.2.3/Data/UsetheHTTPEventCollector) and create a token

Update your .env file with the newly created token value.

restart docker-compose with the new changes

```bash
docker-compose up --build
```


## Payment Service

See [Doc](src/paymentservice/README.md).

## Endpoints

| URL | Method | Description |
| --- | --- | --- |
| [http://localhost:5000/paymentservice/bamboraconfiguration](http://localhost:5050/paymentservice/bamboraconfiguration) | GET | Bambora configuration url |
| [http://localhost:5000/paymentservice/getSinglePaymentURL](http://localhost:5050/paymentservice/getSinglePaymentURL) | GET | Single Payment Url |
| [http://localhost:5000/paymentservice/actuator/health](http://localhost:8081/paymentservice/actuator/health) | GET | Payment Service Health |
| [http://localhost:5000/paymentservice/swagger-ui.html](http://localhost:8081/paymentservice/swagger-ui.html) | GET | Swagger-UI |


