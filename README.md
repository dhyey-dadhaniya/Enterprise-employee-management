# Employee List

This repository is organized with clear separation:

- `frontend/` → Vite + React UI
- `backend/` → Spring Boot Employee API

## Backend (Spring Boot)

### Requirements

- Java 21
- Maven 3.6+
- MySQL 8

### Run backend locally

```bash
cd backend
mvn spring-boot:run
```

Backend runs at `http://localhost:8080`.

### Backend docs

- API and setup: `backend/README.md`
- Database notes: `backend/docs/DATABASE.md`
- Railway deploy: `backend/docs/RAILWAY.md`
- Postman collection: `backend/postman/Employee-List-API.postman_collection.json`

## Frontend (Vite + React)

```bash
cd frontend
npm install
npm run dev
```

- Dev UI proxies `/api` to `http://localhost:8080`.
- Build output: `frontend/dist`.

## Run with Docker Compose

```bash
docker compose up --build
```

- API: `http://localhost:8080`
- MySQL: `localhost:3306` (`root` / `root`, DB `employee_db`)
