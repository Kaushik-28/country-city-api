# Country City API

A simple Spring Boot backend service that exposes REST APIs for countries and cities.

The API allows users to retrieve all countries, fetch paginated cities for a selected country, and get city details by id. It also includes Swagger/OpenAPI documentation, request validation, centralized exception handling, Docker support, an Actuator health check, and a Postman collection for easier testing.

## Tech Stack

This project is built using the following technologies:

| Technology | Purpose |
|---|---|
| Java 21 | Main programming language |
| Spring Boot | Application framework |
| Spring Web | Building REST APIs |
| Spring Validation | Request parameter and input validation |
| Spring Data Commons | Pagination support using `Page`, `Pageable`, and `PageRequest` |
| Lombok | Reducing boilerplate code such as getters, constructors, and equals/hashCode |
| Springdoc OpenAPI / Swagger UI | API documentation and testing through Swagger UI |

## API Documentation

The application uses Springdoc OpenAPI to generate API documentation automatically.

Once the application is running, Swagger UI can be accessed at:

```text
http://localhost:8080/swagger-ui.html
```
OpenAPI JSON is available at:
```text
http://localhost:8080/v3/api-docs
```

## Design Notes

- I used in-memory data because the task mentions that a database is optional.
- The application is still split into controller, service, repository, DTO, exception, and config packages to keep the structure clean.
- I have not returned the internal Country and City objects directly from the APIs. The controller returns response DTOs instead.
- Country and City are kept as simple entity-like classes, but they are not marked with JPA annotations since there is no database in this version.
- Cities are stored against country ids in the repository because the main use case is to fetch cities for a selected country.
- The cities list API uses Spring Data `Pageable` and `PageImpl` for pagination. This should make it easier to replace the in-memory repository with a database repository later.
- Basic validations are added for path variables and pagination parameters.
- A common exception handler is added so that invalid inputs and not-found cases return cleaner error responses.
- Response DTOs are implemented as Java records because they are simple immutable data carriers and keep the API response model concise.
- Also added a Dockerfile, an Actuator health check endpoint, and a Postman collection to make the application easier to run, verify, and test during review.

## API Summary

| Method | Endpoint | Description |
|---|---|---|
| GET | `/countries` | Returns all countries |
| GET | `/countries/{countryId}/cities?page=0&size=4` | Returns paginated cities for a country |
| GET | `/cities/{cityId}` | Returns city details by id |
| GET | `/actuator/health` | Returns application health status |
## To Clone and Run

```bash
git clone https://github.com/Kaushik-28/country-city-api.git
```
Go to the project root directory:

```bash
cd country-city-api
```
## Run with Docker
From the project root:
Build the application jar:

```bash
./mvnw clean package
```
or windows
```windows
mvnw.cmd clean package
```

Build the Docker image:

```bash
docker build -t country-city-api .
```

Run the Docker container:

```bash
docker run --rm -p 8080:8080 country-city-api
```
## Run Locally Without Docker

From the project root, run:

```bash
./mvnw clean spring-boot:run
```
or windows
```windows
mvnw.cmd clean spring-boot:run
```

## URLs
Application URL:

```text
http://localhost:8080
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

Example API:

```text
http://localhost:8080/countries
```

## Health Check

The application exposes a basic Spring Boot Actuator health endpoint:

```text
http://localhost:8080/actuator/health
```

Sample response:

```json
{
  "status": "UP"
}
```
## Postman Collection

A Postman collection (which can be imported) is included for quick API testing.

Location:

```text
postman/country-city-api.postman_collection.json
```
