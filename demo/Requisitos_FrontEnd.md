# 0. Instalación y configuración de PostgreSQL (paso a paso)

Sigue estos pasos para instalar y dejar lista la base de datos en tu computadora:

1. **Descarga PostgreSQL**
  - Ve a https://www.postgresql.org/download/windows/ y descarga el instalador para Windows.
2. **Ejecuta el instalador**
  - Haz doble clic en el archivo descargado.
  - Haz clic en "Siguiente" en cada pantalla hasta llegar a la de contraseña.
  - Cuando te pida la contraseña del usuario `postgres`, escribe: `qa1234` (guárdala, la necesitarás siempre).
  - Sigue dando "Siguiente" hasta finalizar la instalación.
3. **Abre pgAdmin**
  - Busca "pgAdmin" en el menú de inicio y ábrelo.
  - Espera a que cargue y conecta con el servidor (te pedirá la contraseña: `qa1234`).
4. **Crea la base de datos**
  - En el panel izquierdo, haz clic derecho sobre "Databases" y selecciona "Create > Database...".
  - Escribe el nombre: `pymes` y haz clic en "Save".
5. **Ejecuta la query de tablas**
  - Haz clic derecho sobre la base de datos `pymes` > "Query Tool".
  - Copia y pega toda la query de creación de tablas (ver sección siguiente) y haz clic en el botón de ejecutar (ícono de rayo).
  - Espera a que todas las tablas se creen sin errores.
6. **Verifica que todo está bien**
  - En el panel izquierdo, expande la base de datos `pymes` > Schemas > public > Tables. Debes ver todas las tablas creadas.
7. **Configura el backend y prueba la conexión**
  - Abre el archivo `src/main/resources/application.properties` y verifica que los datos de conexión sean:
    ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/pymes
    spring.datasource.username=postgres
    spring.datasource.password=qa1234
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.hibernate.ddl-auto=none
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.application.name=demo
    ```
  - Levanta el backend con `./mvnw.cmd spring-boot:run` (Windows) o `./mvnw spring-boot:run` (Linux/Mac).
  - Si no hay errores y puedes ver el backend en `http://localhost:8080`, ¡ya tienes conexión!


# 0. Query completa para crear la base de datos y todas las tablas

Ejecuta esta query en tu instalación local de PostgreSQL antes de levantar el backend. Esto garantiza que tendrás toda la estructura necesaria para que el backend funcione correctamente.

```sql
-- BASE DE DATOS Y USUARIO
CREATE DATABASE pymes;
ALTER USER postgres WITH PASSWORD 'qa1234';
GRANT ALL PRIVILEGES ON DATABASE pymes TO postgres;

-- USERS, ROLES, AND PERMISSIONS
CREATE TABLE roles (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  description TEXT
);

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE user_roles (
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
  role_id INTEGER REFERENCES roles(id) ON DELETE CASCADE,
  PRIMARY KEY (user_id, role_id)
);

-- ACCOUNTS CATALOG
CREATE TABLE accounts (
  id SERIAL PRIMARY KEY,
  code VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  parent_id INTEGER REFERENCES accounts(id),
  level INTEGER NOT NULL,
  type VARCHAR(20) NOT NULL, -- Asset, Liability, Equity, Income, Expense
  is_detail BOOLEAN DEFAULT TRUE,
  is_active BOOLEAN DEFAULT TRUE,
  nature VARCHAR(10) NOT NULL -- Debit, Credit
);

-- ACCOUNTING PERIODS
CREATE TABLE periods (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  is_closed BOOLEAN DEFAULT FALSE
);

-- JOURNAL ENTRIES (HEADER)
CREATE TABLE journal_entries (
  id SERIAL PRIMARY KEY,
  entry_number VARCHAR(30) NOT NULL UNIQUE,
  date DATE NOT NULL,
  description TEXT NOT NULL,
  status VARCHAR(20) NOT NULL, -- Draft, Posted, Reversed, Cancelled
  period_id INTEGER REFERENCES periods(id),
  created_by INTEGER REFERENCES users(id),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  reversed_entry_id INTEGER REFERENCES journal_entries(id)
);

-- JOURNAL ENTRY LINES (DETAIL)
CREATE TABLE journal_entry_lines (
  id SERIAL PRIMARY KEY,
  journal_entry_id INTEGER REFERENCES journal_entries(id) ON DELETE CASCADE,
  account_id INTEGER REFERENCES accounts(id),
  description TEXT,
  debit NUMERIC(18,2) DEFAULT 0,
  credit NUMERIC(18,2) DEFAULT 0,
  line_number INTEGER
);

-- DOCUMENTS (INVOICES, RECEIPTS, ETC.)
CREATE TABLE documents (
  id SERIAL PRIMARY KEY,
  doc_type VARCHAR(30) NOT NULL, -- Invoice, Receipt, etc.
  doc_number VARCHAR(30) NOT NULL,
  date DATE NOT NULL,
  customer_id INTEGER,
  supplier_id INTEGER,
  total NUMERIC(18,2) NOT NULL,
  status VARCHAR(20) NOT NULL, -- Draft, Posted, Cancelled
  journal_entry_id INTEGER REFERENCES journal_entries(id),
  UNIQUE(doc_type, doc_number)
);

-- CUSTOMERS AND SUPPLIERS
CREATE TABLE customers (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100),
  phone VARCHAR(30),
  address TEXT
);

CREATE TABLE suppliers (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100),
  phone VARCHAR(30),
  address TEXT
);

-- INVENTORY PRODUCTS
CREATE TABLE products (
  id SERIAL PRIMARY KEY,
  sku VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description TEXT,
  unit VARCHAR(20),
  sale_price NUMERIC(18,2),
  cost NUMERIC(18,2),
  is_batch_controlled BOOLEAN DEFAULT FALSE,
  is_active BOOLEAN DEFAULT TRUE
);

-- INVENTORY MOVEMENTS
CREATE TABLE inventory_movements (
  id SERIAL PRIMARY KEY,
  product_id INTEGER REFERENCES products(id),
  movement_type VARCHAR(20) NOT NULL, -- In, Out, Adjustment, Transfer
  quantity NUMERIC(18,2) NOT NULL,
  date DATE NOT NULL,
  document_id INTEGER REFERENCES documents(id),
  warehouse_id INTEGER,
  cost_method VARCHAR(20), -- FIFO, Weighted Average, Specific
  reference TEXT
);

-- ACCOUNTS RECEIVABLE/PAYABLE
CREATE TABLE receivables (
  id SERIAL PRIMARY KEY,
  customer_id INTEGER REFERENCES customers(id),
  document_id INTEGER REFERENCES documents(id),
  due_date DATE,
  amount NUMERIC(18,2) NOT NULL,
  balance NUMERIC(18,2) NOT NULL,
  status VARCHAR(20) NOT NULL -- Open, Paid, Overdue, Cancelled
);

CREATE TABLE payables (
  id SERIAL PRIMARY KEY,
  supplier_id INTEGER REFERENCES suppliers(id),
  document_id INTEGER REFERENCES documents(id),
  due_date DATE,
  amount NUMERIC(18,2) NOT NULL,
  balance NUMERIC(18,2) NOT NULL,
  status VARCHAR(20) NOT NULL -- Open, Paid, Overdue, Cancelled
);

-- BANKS AND CASH
CREATE TABLE bank_accounts (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  account_number VARCHAR(50) NOT NULL,
  bank_name VARCHAR(100),
  currency VARCHAR(10),
  is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE bank_movements (
  id SERIAL PRIMARY KEY,
  bank_account_id INTEGER REFERENCES bank_accounts(id),
  date DATE NOT NULL,
  movement_type VARCHAR(20) NOT NULL, -- Deposit, Withdrawal, Transfer, Check
  amount NUMERIC(18,2) NOT NULL,
  description TEXT,
  reconciled BOOLEAN DEFAULT FALSE,
  document_id INTEGER REFERENCES documents(id)
);

-- AUDIT LOG
CREATE TABLE audit_log (
  id SERIAL PRIMARY KEY,
  table_name VARCHAR(50) NOT NULL,
  record_id INTEGER NOT NULL,
  action VARCHAR(20) NOT NULL, -- Insert, Update, Delete
  changed_by INTEGER REFERENCES users(id),
  changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  old_data JSONB,
  new_data JSONB
);
---


# 2. Pruebas y uso de cada módulo (paso a paso)

## 2.1. Inicio de sesión y autenticación

**Endpoint para login:**
- `POST http://localhost:8080/api/auth/login`

**Ejemplo de JSON:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Cómo probar en Postman:**
1. Haz un POST a `http://localhost:8080/api/auth/login` con el JSON anterior.
2. Copia el token JWT que recibes en la respuesta.
3. Usa ese token en la cabecera `Authorization: Bearer <token>` para todas las siguientes peticiones.

---

## 2.2. Usuarios y roles

**Registro de usuario:**
- `POST http://localhost:8080/api/auth/register`

**Ejemplo de JSON:**
```json
{
  "username": "usuario1",
  "password": "clave123",
  "role": "ADMIN"
}
```

**Probar en Postman:**
1. Haz un POST a `http://localhost:8080/api/auth/register` con el JSON anterior.
2. Luego haz login para obtener el token.

---

## 2.3. Catálogo de cuentas contables

**Endpoints principales:**
- `GET http://localhost:8080/api/accounts` — Listar cuentas
- `POST http://localhost:8080/api/accounts` — Crear cuenta
- `PUT http://localhost:8080/api/accounts/{id}` — Actualizar cuenta
- `DELETE http://localhost:8080/api/accounts/{id}` — Eliminar cuenta

**Probar en Postman:**
1. Usa el token JWT en la cabecera.
2. Haz peticiones a los endpoints anteriores según la operación deseada.

---

## 2.4. Asientos contables (Journal Entries)

**Endpoints principales:**
- `GET http://localhost:8080/api/journal-entries` — Listar asientos
- `POST http://localhost:8080/api/journal-entries` — Crear asiento
- `GET http://localhost:8080/api/journal-entries/{id}` — Consultar asiento

**Probar en Postman:**
1. Usa el token JWT en la cabecera.
2. Haz peticiones a los endpoints anteriores.

---

## 2.5. Clientes y proveedores

**Clientes:**
- `GET http://localhost:8080/api/customers` — Listar
- `POST http://localhost:8080/api/customers` — Crear
- `PUT http://localhost:8080/api/customers/{id}` — Actualizar
- `DELETE http://localhost:8080/api/customers/{id}` — Eliminar

**Proveedores:**
- `GET http://localhost:8080/api/suppliers` — Listar
- `POST http://localhost:8080/api/suppliers` — Crear
- `PUT http://localhost:8080/api/suppliers/{id}` — Actualizar
- `DELETE http://localhost:8080/api/suppliers/{id}` — Eliminar

**Probar en Postman:**
1. Usa el token JWT en la cabecera.
2. Haz peticiones a los endpoints de clientes o proveedores.

---

## 2.6. Productos y servicios

**Endpoints principales:**
- `GET http://localhost:8080/api/products` — Listar
- `POST http://localhost:8080/api/products` — Crear
- `PUT http://localhost:8080/api/products/{id}` — Actualizar
- `DELETE http://localhost:8080/api/products/{id}` — Eliminar

**Probar en Postman:**
1. Usa el token JWT en la cabecera.
2. Haz peticiones a los endpoints anteriores.

---

## 2.7. Facturación (ventas)

**Endpoints principales:**
- `GET http://localhost:8080/api/invoices` — Listar facturas
- `POST http://localhost:8080/api/invoices` — Crear factura
- `GET http://localhost:8080/api/invoices/{id}` — Consultar factura

**Probar en Postman:**
1. Usa el token JWT en la cabecera.
2. Haz peticiones a los endpoints anteriores.

---

## 2.8. Reportes

**Facturas por rango de fechas:**
- `GET http://localhost:8080/api/invoices/report/date-range?start=YYYY-MM-DD&end=YYYY-MM-DD`

**Total de ventas por cliente:**
- `GET http://localhost:8080/api/invoices/report/sales-by-customer/{customerId}`

**Probar en Postman:**
1. Usa el token JWT en la cabecera.
2. Haz peticiones a los endpoints de reportes.

---

