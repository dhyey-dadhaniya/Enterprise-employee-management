# Deploying frontend and backend on Railway (separate services)

Railway can run two services from the same GitHub repo by setting a **different root directory** for each.

## Prerequisites

- Push this repository to GitHub.
- Sign in at [railway.app](https://railway.app) and **New Project** → **Deploy from GitHub** → select the repo.

---

## Backend (Spring Boot)

1. **Add MySQL** to the project: **New** → **Database** → **MySQL**. Railway injects connection variables into linked services.
2. **New** → **GitHub Repo** (or duplicate service) → set **Root Directory** to `backend` (where `pom.xml` lives).
3. **Settings**:
   - **Build**: Nixpacks will detect Java/Maven, or set **Dockerfile** path to `Dockerfile` if you prefer Docker builds.
   - **Start**: default `java -jar target/*.jar` after Maven build, or use the Docker image from the Dockerfile.
4. **Variables** (link MySQL service so variables are available, or set manually):

   | Variable | Notes |
   |----------|--------|
   | `SPRING_DATASOURCE_URL` | JDBC URL to MySQL (from Railway MySQL plugin, often `jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE}` style — copy the **JDBC** URL from Railway’s MySQL tab or compose from `MYSQL_*` vars). |
   | `SPRING_DATASOURCE_USERNAME` | MySQL user |
   | `SPRING_DATASOURCE_PASSWORD` | MySQL password |
   | `PORT` | Railway sets this automatically; Spring Boot reads `server.port=${PORT:8080}`. |

   For Spring Boot on Railway, a common pattern is:

   - No extra mapping needed because `application.properties` already uses `server.port=${PORT:8080}`.

5. Deploy and copy the **public URL** of the backend (e.g. `https://your-api.up.railway.app`).

---

## Frontend (Vite + React)

1. **New** → **GitHub Repo** → same repository.
2. **Root Directory**: `frontend` (where `package.json` and `Dockerfile` are).
3. **Option A – Dockerfile**: set Dockerfile path to `frontend/Dockerfile`. Add a **build argument** `VITE_API_URL` pointing to your backend public URL (Railway → Variables → build-time, or Docker build args).
4. **Option B – Nixpacks**: **Build command**: `npm ci && npm run build`; **Start command**: `npx serve -s dist -l $PORT` (install `serve` or use static hosting).
5. **Build-time variable** for API base URL (Vite embeds `VITE_*` at build time):

   | Variable | Example |
   |----------|---------|
   | `VITE_API_URL` | `https://your-api.up.railway.app` (no trailing slash) |

   Redeploy the frontend after changing `VITE_API_URL`.

6. **CORS**: Backend `WebConfig` already allows all origins for `/api/**`. For production you may restrict `allowedOrigins` to your frontend URL.

---

## Public URLs

After deployment, note:

- **Backend**: `https://<backend-service>.up.railway.app` — health check: `GET /api/employees`
- **Frontend**: `https://<frontend-service>.up.railway.app`

Replace placeholders with your actual Railway hostnames.

---

## Local Docker (reference)

```bash
docker compose up --build
```

API: `http://localhost:8080/api/employees`
