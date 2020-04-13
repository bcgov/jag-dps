# DPS Payment Service

The DPS Payment Service provides **two** operations completely replacing the legacy Payment service:

- **getSinglePaymentURL**: Provides an environmentally specific set of CRC application endpoints.
- **getBeanstreamEndpoints**: Provides an encoded URL for insertion into outgoing SPD client email linking the clients to the Bambora Online Payment System.

Payment service implements [Bambora Hash validation for Checkout](https://help.na.bambora.com/hc/en-us/articles/115010303987-Hash-validation-for-Checkout) to serve secure urls.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.  

See **Deployment** section for additional notes on how to deploy the project on a live system.

### Configuration

You should use environment variables to configure payment service app

> set the `cloud` active profile in order to activate enviroment variables overwriting: `spring_profiles_active=cloud`

Add the following flag

| Environment Variable  | Description   | Notes   |
|---|---|---|
| CRC_ENDPOINT_APPROVED | Credit Record Check Approved Endpoint |  not set by default |
| CRC_ENDPOINT_DECLINED | Credit Record Check Declined Endpoint |  not set by default |
| CRC_ENDPOINT_ERROR | Credit Record Check Error Endpoint |  not set by default |
| BAMBORA_PAYMENT_ENDPOINT | Bambora Payment Endpoint |  not set by default |
| BAMBORA_MERCHANT_ID | Bambora Merchant Endpoint |  not set by default |
| BAMBORA_HASHKEY | Bambora Hashkey |  not set by default |
| BAMBORA_TIME_ZONE | ID - the ID for a TimeZone, either an abbreviation such as "PST", a full name such as "America/Los_Angeles", or a custom ID such as "GMT-8:00". Note that the support of abbreviations is for JDK 1.1.x compatibility only and full names should be used. |  not set by default |
| SWAGGER_UI_ENABLED | Enable Swagger Ui | default is false |

### Health Checks

Payment Service is using (Spring Boot Actuator)[https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-enabling] to expose health endpoints.

The default health is available at `/paymentservice/actuator/health`

### OpenAPI self documentation (Swagger-UI)
 
See [Swagger-UI endpoint](http://localhost:8081/paymentservice/swagger-ui.html)

### Splunk

Payment services support [Splunk](https://www.splunk.com/) for event logging. To enable splunk run the mvn commend with the splunk profile activated

mvn command example:

```
mvn package -P splunk
mvn spring-boot:run -P splunk
```

Configure the following environment variable to start logging data to splunk

| Environment Variable  | Description   | Notes   |
|---|---|---|
| SPLUNK_ULR | Splunk base url |  not set by default |
| SPLUNK_TOKEN | Splunk HEC token value |  not set by default |

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

## local operation endpoints

```
http://localhost:8081/paymentservice/getSinglePaymentURL
http://localhost:8081/paymentservice/getBeanstreamEndpoints
```

## Running the tests

Run  

```
mvn test
```

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


