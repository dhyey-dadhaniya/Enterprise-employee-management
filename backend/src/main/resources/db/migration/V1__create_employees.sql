-- Flyway V1: employees table (MySQL-compatible; matches JPA entity)

CREATE TABLE employees (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL,
    department  VARCHAR(50),
    phone       VARCHAR(20),
    CONSTRAINT uk_employees_email UNIQUE (email)
);

CREATE INDEX idx_employees_department ON employees (department);
