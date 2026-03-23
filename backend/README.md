# Employee Backend - CRUD REST API

Spring Boot REST API for the `Employee` resource using Spring Web, Spring Data JPA, Flyway migrations, MySQL, and Java 21.

## Requirements

- Java 21
- Maven 3.6+
- MySQL 8

## Configuration

Environment variables (or defaults in `src/main/resources/application.properties`):

| Variable | Purpose |
|----------|---------|
| `SPRING_DATASOURCE_URL` | JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | DB user |
| `SPRING_DATASOURCE_PASSWORD` | DB password |
| `PORT` | HTTP port (default `8080`) |

## Run

```bash
mvn spring-boot:run
```

## Test

```bash
mvn test
```

## API Endpoints

| Method | Endpoint |
|--------|----------|
| GET | `/api/employees` |
| GET | `/api/employees/{id}` |
| POST | `/api/employees` |
| PUT | `/api/employees/{id}` |
| DELETE | `/api/employees/{id}` |

## Related Files

- Flyway migrations: `src/main/resources/db/migration/`
- Logging config: `src/main/resources/logback-spring.xml`
- Database notes: `docs/DATABASE.md`
- Railway docs: `docs/RAILWAY.md`
- Postman: `postman/Employee-List-API.postman_collection.json`
