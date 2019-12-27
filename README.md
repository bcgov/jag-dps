# DPS

A Document Processing System.

## Project Structure

    .
    ├── .github                 # Contains GitHub Related sources
    ├── src                     # application source files
    ├── docker-compose.yml      # docker compose definition
    ├── LICENSE                 # Apache License
    └── README.md               # This file.

## Run

Install [Docker](https://www.docker.com/)

run

```bash
docker-compose up
```

Payment Service Endpoints

| URL | Method | Description |
| --- | --- | --- |
| [http://localhost:5050/paymentservice/bamboraconfiguration](http://localhost:5050/paymentservice/bamboraconfiguration) | POST | Bambora configuration url |
| [http://localhost:5050/paymentservice/singlepaymenturl](http://localhost:5050/paymentservice/singlepaymenturl) | POST | Single Payment Url |
