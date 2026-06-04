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

## To Clone and Run

```bash
git clone https://github.com/<your-username>/country-city-api.git
```
Go to the project root directory:

```bash
cd country-city-api
```
## Run with Docker
From the project root:
Build the application jar:

```bash
mvn clean package
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
mvn clean spring-boot:run
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