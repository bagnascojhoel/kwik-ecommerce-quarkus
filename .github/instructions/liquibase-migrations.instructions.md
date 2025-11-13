---
applyTo: 'src/main/resources/migrations/**/*.sql, src/main/resources/migrations/**/changelog.xml'
---

Liquibase migration standards for SQLite database schema management.

## Migration Structure

- **Location:** `src/main/resources/migrations/{domain}/`
- **Domains:** `auth/`, `product/`, `tenant/` (add new domains as needed)
- **Master changelog:** `changelog.xml` uses `includeAll` for automatic discovery
- **Rollback scripts:** Optional `{domain}/rollback/` subdirectories

## File Naming

- **Pattern:** `{number}-{descriptive-kebab-case-name}.sql`
- **Examples:** `1-create-user-table.sql`, `2-add-kwik-user.sql`
- **Ordering:** Sequential numbers ensure execution order within domain

## Changeset Format

**Required header:**
```sql
--liquibase formatted sql

-- changeset bagnascojhoel:{domain}-{number}-{descriptive-name} context:{domain}
```

**Changeset ID components:**
- Author: `bagnascojhoel`
- Format: `{domain}-{number}-{descriptive-name}`
- Context: Optional, use domain name (e.g., `context:auth`)

## SQL Conventions

- **Keywords:** UPPERCASE (CREATE, TABLE, VARCHAR, NOT NULL, etc.)
- **Identifiers:** lowercase with underscores (table_name, column_name)
- **Tables with reserved words:** Single quotes (`'user'`)
- **Primary keys:** `id INTEGER PRIMARY KEY AUTOINCREMENT`
- **Foreign keys:** Inline with explicit syntax `FOREIGN KEY (col) REFERENCES table (id)`

## Audit Columns

Include standard audit fields on all tables:
```sql
created_by VARCHAR(50) NOT NULL,
created_at DATETIME NOT NULL,
modified_by VARCHAR(50),
modified_at DATETIME
```

## Data Types

- **IDs:** INTEGER
- **Short text:** VARCHAR(50)
- **Medium text:** VARCHAR(300)
- **Currency:** NUMERIC
- **State/Enum:** VARCHAR(30)
- **Timestamps:** DATETIME

## Examples

**Table creation:**
```sql
CREATE TABLE product (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(300) NOT NULL,
    tenant_id INTEGER NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (tenant_id) REFERENCES tenant (id)
);
```

**Data insertion:**
```sql
INSERT INTO "user" (username, email, created_by, created_at)
VALUES ('admin', 'admin@example.com', 'liquibase', CURRENT_TIMESTAMP);
```
