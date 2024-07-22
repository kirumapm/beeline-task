# Order processing system

[Техническое задание](..)

## Contents

1. [Launch](#guide)
2. [Stack](#stack)
2. [Credentials](#creds)

## Launch <a id="guide"></a>
(you have to wait 30-40 sec until keycloak fully completes launch)
```shell
git clone https://github.com/kirumapm/beeline-task
cd beeline-task
docker compose up
```
then go to http://localhost:8080

kafka-ui http://localhost:9090

keycloak http://localhost:8082 (name: admin, password: admin)

## Stack <a id="stack"></a>

- Java, Keycloak, OAuth2/OIDC, Kafka

## Credentials(you can create a new one) <a id="creds"></a>

| username  | password |
|:----------|----------|
| j.daniels | password | 