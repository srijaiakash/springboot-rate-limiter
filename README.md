# Spring Boot Rate Limiter

A simple API Rate Limiter built using Spring Boot using the Fixed Window algorithm.

## Features

- Fixed Window Rate Limiting
- Per-IP request throttling
- Thread-safe request tracking using ConcurrentHashMap
- JSON error response
- Swagger/OpenAPI integration
- HTTP 429 response handling

## Tech Stack

- Java 17
- Spring Boot
- Maven
- Swagger/OpenAPI

## API Endpoint

```http
GET /api/test
```

## Rate Limit

- 5 requests per minute

## Error Response

```json
{
  "status": 429,
  "message": "Rate limit exceeded"
}
```

## Run Locally

```bash
mvn spring-boot:run
```

## Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```
