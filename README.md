# Spring Boot JPA: The N+1 Query Problem (`1,001 -> 1` Query)

This repository accompanies the YouTube deep dive on diagnosing and fixing the **Hibernate N+1 Query Problem** using **Spring Boot 4, Java 25, and PostgreSQL**.

It demonstrates how a standard `@OneToMany(fetch = FetchType.LAZY)` relationship between `PurchaseOrder` and `OrderLine` silently triggers **1,001 separate SQL queries** during a single `GET /orders` request—and how a single line of JPQL (`LEFT JOIN FETCH`) collapses those 1,001 database round-trips down to **1 SQL query**.

---

## 1. Prerequisites

Ensure the following tools are installed on your local machine before running the project:

* **Java 25** (or Java 21+) — Verify with `java -version`
* **Maven 3.9+** (or use the included `./mvnw` wrapper)
* **PostgreSQL 18** (or PostgreSQL 15+) running on **`localhost:5433`**
* **`cURL`** (for hitting the REST endpoint from the command line)

---

## 2. Database Setup & SQL Schema

### Step 1: Create the Database, User, and Schema Permissions
In PostgreSQL 15+ (including PostgreSQL 18), non-superusers do not have `CREATE` privileges on the `public` schema by default. 

Connect to your PostgreSQL instance on port **`5433`** as the `postgres` superuser and run the following SQL script:

```sql
-- 1. Create the demo user and orders database
CREATE USER demo WITH PASSWORD 'demo';
CREATE DATABASE orders OWNER demo;

-- 2. Connect to the orders database
\c orders

-- 3. Reset the public schema and grant full ownership to demo
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;
ALTER SCHEMA public OWNER TO demo;
GRANT ALL ON SCHEMA public TO demo;
GRANT ALL ON DATABASE orders TO demo;
