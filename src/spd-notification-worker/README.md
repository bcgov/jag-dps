# SPD Notification worker

This Application subscribe to CRRP Output Notification and update FIGARO database

## Run

```bash
mvn spring-boot:run
```

## Endpoints

| endpoint | Verb | Description |
| --- | --- | --- |
| [http://localhost:8080/spdnotificationworker/actuator/health](http://localhost:8080/dpsnotificationservice/actuator/health) | GET | DPS Notification Service Health |

## Configuration

You should use environment variables to configure payment service app

> set the `cloud` active profile in order to activate environment variables overwriting: `spring_profiles_active=cloud`

### rabbitmq

| Environment Variable  | Description   | Default value |
|---|---|---|
| RABBITMQ_HOST | RabbitMq Host |  `localhost` |
| RABBITMQ_PORT | RabbitMq Port |  `5672` |
| RABBITMQ_USERNAME | RabbitMq Username | `guest` |
| RABBITMQ_PASSWORD | RabbitMq Password | `guest` |

### ORDS

| Environment Variable  | Description   | Notes   |
|---|---|---|
| ORDS_BASE_PATH | ORDS base path | - |
| ORDS_USERNAME | ORDS username | - |
| ORDS_PASSWORD | ORDS password | - |

### SFTP

| Environment Variable  | Description  | Notes  |
|---|---|---|
| DPS_SFTP_HOST:localhost} | sftp host | `localhost` |
| DPS_SFTP_PORT:22 | sftp port | `22` |
| DPS_SFTP_USERNAME | sftp username | - |
| DPS_SFTP_PASSWORD | sftp password | - |
| DPS_SFTP_KNOWNHOSTS | know hosts file path | - |
| DPS_SFTP_REMOTE_LOCATION | sftp remote location base directory | `upload` |
| DPS_SFTP_PRIVATE_KEY | sftp private key value | |
| DPS_SFTP_PRIVATE_PASSPHRASE | sftp passphrase | |
| DPS_SFTP_ALLOW_UNKNOWN_KEYS | sftp allow unknowns keys | `false` |

