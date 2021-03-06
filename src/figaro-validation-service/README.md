# FIGARO Validation Service 

The FIGARO Validation Service provides **seven** operations completely replacing the legacy FIGARO Validation services previously hosted on webMethods.

Health check and self documenting Swagger-UI endpoints are also included.  

These include: 

- **locateMatchingApplicants**
- **validateApplicantForSharing**
- **validateApplicantPartyId**
- **validateApplicantService**
- **validateFacilityParty**
- **validateOrgDrawDownBalance**
- **validateOrgParty**

## Getting Started

See **Installing** section for running the application locally. Refer to the [https://github.com/bcgov/jag-dps/blob/master/README.md](https://github.com/bcgov/jag-dps/blob/master/README.md) on how to 
build and deploy the project within a Docker container.

### Configuration

Environment variables should be used to configure the Figaro Validation service app

> set the `cloud` active profile in order to activate environment variables overwriting: `spring_profiles_active=cloud`

Add the following flags

| Environment Variable  | Description   | Notes   |
| --- | --- | --- |
| FIGCR_BASE_URL | ORDS FIG SERVICE URL |   |
| FIGCR_USERNAME | ORDS FIG SERVICE USERNAME |   |
| FIGCR_PASSWORD | ORDS FIG SERVICE PASSWORD |   |

### Health Checks

This service uses Spring Boot Actuator [https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-enabling](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-enabling) to expose health endpoints.

The default health is available at `/figvalidationservice/actuator/health`

### OpenAPI self documentation (Swagger-UI)
 
See [Swagger-UI endpoint](http://localhost:8081/figvalidationservice/swagger-ui.html)
 
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
http://localhost:8081/figvalidationservice/locateMatchingApplicants
http://localhost:8081/figvalidationservice/validateApplicantService
http://localhost:8081/figvalidationservice/validateApplicantForSharing
http://localhost:8081/figvalidationservice/validateApplicantPartyId

others when complete here

http://localhost:8081/figvalidationservice/actuator/health 
http://localhost:8081/figvalidationservice/swagger-ui.html
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




