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

run

```bash
docker-compose up
```

## Payment Service

See [Doc](src/paymentservice/README.md).

## Endpoints

| URL | Method | Description |
| --- | --- | --- |
| [http://localhost:5000/paymentservice/bamboraconfiguration](http://localhost:5050/paymentservice/bamboraconfiguration) | GET | Bambora configuration url |
| [http://localhost:5000/paymentservice/getSinglePaymentURL](http://localhost:5050/paymentservice/getSinglePaymentURL) | GET | Single Payment Url |
