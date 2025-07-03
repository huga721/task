# Betacom Task Application

A Spring Boot REST API with JWT-based authentication, allowing users to register, log in, and manage their own items.

## Features

- User registration & login with JWT token
- Secure endpoints with JWT
- CRUD for items (only visible for the current user)
- MySQL database
- Dockerized application with Docker Compose

## Technologies

- Java 17
- Spring Boot 3
- Spring Security + JWT
- MySQL
- Docker + Docker Compose

## Endpoints

- `POST /auth/register` — register a new user
- `POST /auth/login` — login and receive a JWT token
- `GET /v1/items` — get items for current user (JWT required)
- `POST /v1/items` — add item for current user (JWT required)

## How to Run

### 1. Build the application

```bash
./mvnw clean package -DskipTests
```

### 2. Run with Docker Compose

```bash
docker compose up --build
```
