# DPS OUTPUT NOTIFICATION SERVICE

DPS Output Notification Service dispatch messages to notification workers. When a message is received at the notification endpoint, the notification service determines the tenant and queue a new message for the appropriate notification worker.

## Run

```bash
mvn spring-boot:run
```

## Endpoints

| endpoint | Verb | Description |
| --- | --- | --- |
| [http://localhost:8080/dpsnotificationservice/actuator/health](http://localhost:8080/dpsnotificationservice/actuator/health) | GET | DPS Notification Service Health |
| [http://localhost:8080/dpsnotificationservice/ws/dpsOutputNotification.wsdl](http://localhost:8080/dpsnotificationservice/ws/dpsOutputNotification.wsdl) | GET | DPS Output Notification Service WSDL |

## Configuration

You should use environment variables to configure payment service app

| Environment Variable  | Description   | Notes   |
|---|---|---|
| RABBITMQ_HOST | RabbitMq Host |  set to `localhost` by default |
| RABBITMQ_PORT | RabbitMq Port |  set to `5672` by default |
| RABBITMQ_USERNAME | RabbitMq Username |  set to `guest` by default |
| RABBITMQ_PASSWORD | RabbitMq Password |  set to `guest` by default |

## TEST

```bash
mvn test
```