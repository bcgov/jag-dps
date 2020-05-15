# DPS OUTPUT NOTIFICATION SERVICE

DPS Output Notification Service dispatch messages to notification workers. When a message is received at the notification endpoint, the notification service determines the tenant and queue a new message for the appropriate notification worker.

## Run

```bash
mvn spring-boot:run
```

## Endpoints

| endpoint | Verb | Description |
| --- | --- | --- |
| [http://localhost:8080/dpsregistrationapi/actuator/health](http://localhost:8080/dpsregistrationapi/actuator/health) | GET | DPS Notification Service Health |
| [http://localhost:8080/dpsregistrationapi/ws/dpsregistrationservice.wsdl](http://localhost:8080/dpsregistrationapi/ws/dpsregistrationservice.wsdl) | GET | DPS Output Notification Service WSDL |

## Configuration

You should use environment variables to configure payment service app

| Environment Variable  | Description   | Notes   |
|---|---|---|
| PORT | Web Server application PORT |  set to `8080` by default |
| WS_LOGGING_ENABLED | Enables SOAP Service logging | set to `false` by default |

## TEST

```bash
mvn test
```