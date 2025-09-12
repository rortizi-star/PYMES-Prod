# Validaciones en facturación

* No se permite crear facturas sin cliente, sin líneas, ni líneas con cantidad o precio menor o igual a cero.
* Se valida que el cliente y los productos existan.
* Si alguna validación falla, se retorna un error con el mensaje correspondiente.

# Reportes de facturación

## Facturas por rango de fechas
`GET /api/invoices/report/date-range?start=2025-09-01&end=2025-09-09`
Devuelve todas las facturas emitidas entre las fechas indicadas (inclusive).

## Total de ventas por cliente
`GET /api/invoices/report/sales-by-customer/{customerId}`
Devuelve el total vendido a un cliente específico.
# Módulo: Facturación (Invoice)

Este módulo permite gestionar las facturas de venta. Incluye operaciones para crear, consultar, actualizar y eliminar facturas, así como sus líneas (productos vendidos).

## Endpoints principales

- `GET /api/invoices` — Lista todas las facturas
- `GET /api/invoices/{id}` — Consulta una factura por ID
- `POST /api/invoices` — Crea una nueva factura
- `PUT /api/invoices/{id}` — Actualiza una factura existente
- `DELETE /api/invoices/{id}` — Elimina una factura

### Ejemplo de JSON para crear una factura
```json
{
  "customer": { "id": 1 },
  "date": "2025-09-09",
  "lines": [
    { "product": { "id": 1 }, "quantity": 2, "price": 150.0 },
    { "product": { "id": 2 }, "quantity": 1, "price": 300.0 }
  ]
}
```

### Pruebas rápidas con Postman
1. Inicia sesión y obtén el token JWT.
2. Usa el token en la cabecera `Authorization: Bearer <token>`.
3. Realiza peticiones a los endpoints anteriores.
# Módulo: Productos (Product)

Este módulo permite gestionar los productos y servicios de la empresa. Incluye operaciones para crear, consultar, actualizar y eliminar productos.

## Endpoints principales

- `GET /api/products` — Lista todos los productos
- `GET /api/products/{id}` — Consulta un producto por ID
- `POST /api/products` — Crea un nuevo producto
- `PUT /api/products/{id}` — Actualiza un producto existente
- `DELETE /api/products/{id}` — Elimina un producto

### Ejemplo de JSON para crear un producto
```json
{
  "name": "Servicio de Consultoría",
  "description": "Consultoría en sistemas contables",
  "price": 1500.00,
  "unit": "servicio"
}
```

### Pruebas rápidas con Postman
1. Inicia sesión y obtén el token JWT.
2. Usa el token en la cabecera `Authorization: Bearer <token>`.
3. Realiza peticiones a los endpoints anteriores.
# Sistema de Contabilidad - Backend (Spring Boot)

Este proyecto es un backend para un sistema contable/ERP, desarrollado en Java 21 con Spring Boot 3.5.5 y PostgreSQL. Incluye autenticación JWT, gestión de usuarios, roles, cuentas, asientos contables, clientes y proveedores.

---

# Módulo: Clientes (Customer)

Este módulo permite gestionar los clientes de la empresa. Incluye operaciones para crear, consultar, actualizar y eliminar clientes.

## Endpoints principales

- `GET /api/customers` — Lista todos los clientes
- `GET /api/customers/{id}` — Consulta un cliente por ID
- `POST /api/customers` — Crea un nuevo cliente
- `PUT /api/customers/{id}` — Actualiza un cliente existente
- `DELETE /api/customers/{id}` — Elimina un cliente

### Ejemplo de JSON para crear un cliente
```json
{
  "name": "Empresa ABC",
  "email": "contacto@abc.com",
  "phone": "555-1234",
  "address": "Ciudad, País"
}
```

### Pruebas rápidas con Postman
1. Inicia sesión y obtén el token JWT.
2. Usa el token en la cabecera `Authorization: Bearer <token>`.
3. Realiza peticiones a los endpoints anteriores.

---

# Módulo: Proveedores (Supplier)

Este módulo permite gestionar los proveedores de la empresa. Incluye operaciones para crear, consultar, actualizar y eliminar proveedores.

## Endpoints principales

- `GET /api/suppliers` — Lista todos los proveedores
- `GET /api/suppliers/{id}` — Consulta un proveedor por ID
- `POST /api/suppliers` — Crea un nuevo proveedor
- `PUT /api/suppliers/{id}` — Actualiza un proveedor existente
- `DELETE /api/suppliers/{id}` — Elimina un proveedor

### Ejemplo de JSON para crear un proveedor
```json
{
  "name": "Proveedor XYZ",
  "email": "ventas@xyz.com",
  "phone": "555-5678",
  "address": "Ciudad, País"
}
```

### Pruebas rápidas con Postman
1. Inicia sesión y obtén el token JWT.
2. Usa el token en la cabecera `Authorization: Bearer <token>`.
3. Realiza peticiones a los endpoints anteriores.

---

# Módulo: Bancos y Caja

Este módulo permite gestionar bancos, cuentas bancarias, caja chica y sus movimientos.

## Endpoints principales

### Bancos
- `GET /api/banks` — Lista todos los bancos
- `GET /api/banks/{id}` — Consulta un banco por ID
- `POST /api/banks` — Crea un nuevo banco
- `PUT /api/banks/{id}` — Actualiza un banco existente
- `DELETE /api/banks/{id}` — Elimina un banco
- `GET /api/banks/{id}/accounts` — Lista cuentas bancarias de un banco

### Cuentas bancarias
- `GET /api/banks/accounts/{accountId}/transactions` — Lista transacciones de una cuenta
- `GET /api/banks/accounts/{accountId}/balance` — Consulta saldo de una cuenta
- `POST /api/banks/accounts/{accountId}/transactions` — Crea una transacción bancaria

### Caja chica
- `GET /api/cash-registers` — Lista todas las cajas
- `GET /api/cash-registers/{id}` — Consulta una caja por ID
- `POST /api/cash-registers` — Crea una nueva caja
- `PUT /api/cash-registers/{id}` — Actualiza una caja
- `DELETE /api/cash-registers/{id}` — Elimina una caja
- `GET /api/cash-registers/{id}/transactions` — Lista transacciones de caja
- `GET /api/cash-registers/{id}/balance` — Consulta saldo de caja
- `POST /api/cash-registers/{id}/transactions` — Crea una transacción de caja

### Ejemplo de JSON para crear una cuenta bancaria
```json
{
  "accountNumber": "1234567890",
  "description": "Cuenta corriente principal",
  "balance": 10000.0,
  "bank": { "id": 1 }
}
```

### Ejemplo de JSON para crear una transacción bancaria
```json
{
  "amount": 500.0,
  "description": "Depósito inicial"
}
```

### Ejemplo de JSON para crear una caja chica
```json
{
  "name": "Caja Sucursal Centro",
  "description": "Caja para gastos menores",
  "balance": 500.0
}
```

### Ejemplo de JSON para crear una transacción de caja
```json
{
  "amount": -100.0,
  "description": "Compra de papelería"
}
```

### Pruebas rápidas con Postman
1. Inicia sesión y obtén el token JWT.
2. Usa el token en la cabecera `Authorization: Bearer <token>`.
3. Realiza peticiones a los endpoints anteriores.

---
