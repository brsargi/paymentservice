# Payment Service

## Description

This application is built with Spring Boot and offers the following core features:
1. Storing webhook configurations.
2. Handling payment records and notifying all registered webhooks via HTTP when a payment is processed.

## Requirements

- Docker
- Docker Compose

## Launching the Application

### Using Docker Compose

To get the application running with Docker Compose, follow the steps below:

1. Clone the repository:

    ```bash
    git clone https://github.com/brsargi/paymentservice.git
    cd paymentservice
    ```

2. Start the services:

    ```bash
    docker-compose up
    ```

### Service URLs

- **Spring Boot Application**: `http://localhost:8080/swagger-ui.html`
- **RabbitMQ Management Interface**: `http://localhost:15672` (default credentials: guest/guest)
