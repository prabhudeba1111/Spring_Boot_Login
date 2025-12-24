# Spring Boot Login Application

**Project:** Spring Boot Login

A simple, production-ready Java Spring Boot backend that implements user authentication (registration, login) and basic user management. This README provides quick setup, configuration, common endpoints, and deployment notes so you can run and extend the project locally or in Docker.

---

# Features

* User registration and login
* Password hashing (BCrypt or similar)
* Basic user profile / management endpoints
* JWT-based authentication (configurable) or session-based option
* Validation and error handling
* Clean project structure suitable for extension

---

# Prerequisites

* Java 17 or later (Java 11 may work depending on the code)
* Maven or Gradle (project includes a wrapper: `./mvnw` or `./gradlew`)
* Git
* PostgreSQL or MySQL if not using the bundled H2 database

---

# Quick start

### 1. Clone the repo

```bash
git clone https://github.com/prabhudeba1111/Spring_Boot_Login.git
cd Spring_Boot_Login-main
```

### 2. Build & run with Maven wrapper

```bash
./mvnw clean package
./mvnw spring-boot:run
```

Or using Gradle wrapper:

```bash
./gradlew bootRun
```

By default the app runs on port `8080`. Visit `http://localhost:8080/actuator/health` to verify.

---

# Configuration

Configuration is stored in `src/main/resources/application.properties` or `application.yml`.

Common properties to check/update:

```properties
# Server
server.port=8080

# Datasource (Postgres example)
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# JWT example
app.jwt.secret=replace_with_a_strong_secret
app.jwt.expiration-ms=3600000

# H2 console (if H2 is used)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

---

# Database

The project commonly supports either an embedded H2 DB (development) or an external RDBMS (Postgres/MySQL) in production.

### H2 (development)

If no datasource is configured, the app might default to H2. Access H2 console at `http://localhost:8080/h2-console` with JDBC URL `jdbc:h2:mem:testdb` unless otherwise configured.

### PostgreSQL example

Create a database and user first:

```sql
CREATE DATABASE spring_login;
CREATE USER spring_user WITH PASSWORD 'change_me';
GRANT ALL PRIVILEGES ON DATABASE spring_login TO spring_user;
```

Then set the `spring.datasource.*` properties in `application.properties`.

---

# API Endpoints (common patterns)

* `POST /api/auth/register` — Register a new user

  * Body: `{ "username": "alice", "email": "a@b.com", "password": "secret" }`
* `POST /api/auth/login` — Login and receive JWT

  * Body: `{ "username": "alice", "password": "secret" }`
  * Response: `{ "accessToken": "<jwt>", "tokenType": "Bearer" }`
* `GET /api/users/me` — Get current user (Authorization: `Bearer <token>`)
* `GET /api/users` — (Admin) list users
* `PUT /api/users/{id}` — Update user

### Sample curl (login)

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret"}'
```

---

# Authentication

This project uses JWT for stateless authentication. Key notes:

* A strong secret (`app.jwt.secret`) is required for signing tokens.
* Token expiration should be reasonably short (1h–24h). Use refresh tokens for long-lived sessions.
* Protect endpoints with `@PreAuthorize`, `@Secured`, or WebSecurityConfigurerAdapter/ SecurityFilterChain.

---
