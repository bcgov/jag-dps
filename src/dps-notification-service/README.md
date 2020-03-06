# DPS OUTPUT NOTIFICATION SERVICE

DPS Output Notification Service dispatch messages to notification workers. When a message is received at the notification endpoint, the notification service determines the tenant and queue a new message for the appropriate notification worker.

## RUN

```bash
mvn spring-boot:run
```

## TEST

```bash
mvn test
```