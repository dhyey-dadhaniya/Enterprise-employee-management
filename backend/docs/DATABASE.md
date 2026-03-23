# Employee database (main application)

## Schema

Managed by **Flyway** under `src/main/resources/db/migration/`:

| File | Purpose |
|------|---------|
| `V1__create_employees.sql` | Creates `employees` with unique `email` |
| `V2__seed_employees.sql` | Inserts three sample rows |

JPA uses `spring.jpa.hibernate.ddl-auto=validate` so Hibernate does not alter the schema; Flyway owns it.

## Configuration

- Default: MySQL `employee_db` (see `application.properties`).
- Override with `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` (Docker / Railway).

## Sample SQL

```sql
-- All employees
SELECT id, name, email, department, phone FROM employees ORDER BY id;

-- By department
SELECT * FROM employees WHERE department = 'Engineering';

-- Count by department
SELECT department, COUNT(*) AS cnt FROM employees GROUP BY department;
```

