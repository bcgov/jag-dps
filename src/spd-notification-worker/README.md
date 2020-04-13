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

| Environment Variable  | Description | required | Default value |
| --- | --- | --- | --- |
| RABBITMQ_HOST | RabbitMq Host | optional | `localhost` |
| RABBITMQ_PORT | RabbitMq Port | optional | `5672` |
| RABBITMQ_USERNAME | RabbitMq Username | optional | `guest` |
| RABBITMQ_PASSWORD | RabbitMq Password | optional | `guest` |

### ORDS

| Environment Variable  | Description | required | Default value |
| --- | --- | --- | --- |
| ORDS_BASE_PATH | ORDS base path | **required** | - |
| ORDS_USERNAME | ORDS username | **required** | - |
| ORDS_PASSWORD | ORDS password | **required** | - |

### SFTP

| Environment Variable  | Description | required | Default value |
| --- | --- | --- | --- |
| DPS_SFTP_HOST | sftp host | optional | `localhost` |
| DPS_SFTP_PORT | sftp port | optional | `22` |
| DPS_SFTP_USERNAME | sftp username | optional | - |
| DPS_SFTP_PASSWORD | sftp password | optional | - |
| DPS_SFTP_KNOWNHOSTS | know hosts file path | optional | - |
| DPS_SFTP_REMOTE_LOCATION | sftp remote location base directory | optional | `upload` |
| DPS_SFTP_PRIVATE_KEY | sftp private key value | optional | - |
| DPS_SFTP_PRIVATE_PASSPHRASE | sftp passphrase | optional | - |
| DPS_SFTP_ALLOW_UNKNOWN_KEYS | sftp allow unknowns keys | optional | `false` |

### Splunk

> set the `splunk` active profile in order to ship logs to splunk

| Environment Variable  | Description | required | Default value |
| --- | --- | --- | --- |
| SPLUNK_URL | splunk url | **required** | - |
| SPLUNK_TOKEN | splunk token | **required** | - |
