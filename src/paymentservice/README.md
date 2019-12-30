# DPS Payment Service

The DPS Payment Service provides **two** operations completely replacing the legacy Payment service:

- **getSinglePaymentURL**: Provides an environmentally specific set of CRC application endpoints.
- **getBeanstreamEndpoints**: Provides an encoded URL for insertion into outgoing SPD client email linking the clients to the Bambora Online Payment System.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Configuration

You should use environment variables to configure payment service app

The existing environment variables are explained below:

| Environment Variable  | Description   | Notes   |
|---|---|---|
| CRC_ENDPOINT_APPROVED | Credit Record Check Approved Endpoint |  not set by default |
| CRC_ENDPOINT_DECLINED | Credit Record Check Declined Endpoint |  not set by default |
| CRC_ENDPOINT_ERROR | Credit Record Check Error Endpoint |  not set by default |

### Installing

Run

```
mvn clean install
```

## Running the application 

Run

```
mvn spring-boot:run
```

## local endpoints

```
http://localhost:8081/paymentservice/getSinglePaymentURL
http://localhost:8081/paymentservice/getBeanstreamEndpoints
```

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

TBD

## Contributing

TBD

## Versioning

TBD

## Authors

TBD

## Acknowledgments

TBD


