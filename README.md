# Módulo: Auditoría

Este módulo registra y permite consultar el historial de operaciones clave (altas, bajas, modificaciones, accesos, movimientos críticos) en todo el sistema, para trazabilidad y cumplimiento.

## Entidad principal
- **AuditLog**: registro de auditoría (usuario, acción, entidad, id, detalles, fecha/hora, IP).

## Endpoints principales
- `GET /api/audit` — Lista todo el historial de auditoría
- `GET /api/audit/user/{username}` — Por usuario
- `GET /api/audit/action/{action}` — Por tipo de acción (CREATE, UPDATE, DELETE, LOGIN, LOGOUT, etc.)
- `GET /api/audit/entity/{entity}` — Por entidad (nombre de la tabla o módulo)
- `GET /api/audit/date-range?start=YYYY-MM-DDTHH:mm:ss&end=YYYY-MM-DDTHH:mm:ss` — Por rango de fecha/hora

## Ejemplo de registro automático
Cada vez que un usuario crea, modifica, elimina o accede a información relevante, se genera un registro de auditoría con:
- Usuario
- Acción (CREATE, UPDATE, DELETE, LOGIN, LOGOUT, etc.)
- Entidad y ID
- Detalles (cambios, datos relevantes)
- Fecha/hora
- IP

## Validaciones y reglas
- Solo usuarios autenticados pueden consultar el historial.
- El registro es automático y transparente para el usuario.

## Pruebas rápidas con Postman
1. Inicia sesión y obtén el token JWT.
2. Usa el token en la cabecera `Authorization: Bearer <token>`.
3. Realiza operaciones en cualquier módulo (crear, editar, eliminar, login, etc.).
4. Consulta los registros de auditoría usando los endpoints anteriores.
# Módulo: Bancos y Caja

Este módulo permite gestionar bancos, cuentas bancarias, caja chica, registrar movimientos, transferencias y consultar saldos y reportes.

## Entidades principales
- **Bank**: banco.
- **BankAccount**: cuenta bancaria.
- **BankTransaction**: movimiento bancario (depósito, retiro, transferencia).
- **CashRegister**: caja chica.
- **CashTransaction**: movimiento de caja (ingreso, egreso, transferencia).

## Endpoints principales
### Bancos y cuentas bancarias
- `GET /api/bank/banks` — Lista de bancos
- `GET /api/bank/banks/{id}` — Consulta banco
- `POST /api/bank/banks` — Crear banco
- `DELETE /api/bank/banks/{id}` — Eliminar banco
- `GET /api/bank/accounts` — Lista de cuentas bancarias
- `GET /api/bank/accounts/bank/{bankId}` — Cuentas por banco
- `GET /api/bank/accounts/{id}` — Consulta cuenta bancaria
- `POST /api/bank/accounts` — Crear cuenta bancaria
- `DELETE /api/bank/accounts/{id}` — Eliminar cuenta bancaria

### Caja chica
- `GET /api/bank/cash-registers` — Lista de cajas
- `GET /api/bank/cash-registers/{id}` — Consulta caja
- `POST /api/bank/cash-registers` — Crear caja
- `DELETE /api/bank/cash-registers/{id}` — Eliminar caja

### Movimientos bancarios
- `GET /api/bank/transactions` — Todos los movimientos bancarios
- `GET /api/bank/transactions/account/{accountId}` — Movimientos por cuenta
- `GET /api/bank/transactions/date-range?start=YYYY-MM-DD&end=YYYY-MM-DD` — Movimientos por fecha
- `POST /api/bank/transactions` — Registrar depósito/retiro
- `POST /api/bank/transactions/transfer` — Transferencia entre cuentas

### Movimientos de caja
- `GET /api/bank/cash-transactions` — Todos los movimientos de caja
- `GET /api/bank/cash-transactions/register/{registerId}` — Movimientos por caja
- `GET /api/bank/cash-transactions/date-range?start=YYYY-MM-DD&end=YYYY-MM-DD` — Movimientos por fecha
- `POST /api/bank/cash-transactions` — Registrar ingreso/egreso
- `POST /api/bank/cash-transactions/transfer` — Transferencia entre cajas

### Reportes
- `GET /api/bank/accounts/total-balance` — Suma de saldos bancarios
- `GET /api/bank/cash-registers/total-balance` — Suma de saldos en caja

## Ejemplo de JSON para registrar movimiento bancario
```json
{
	"accountId": 1,
	"amount": 500.0,
	"type": "DEPOSIT", // o "WITHDRAWAL"
	"description": "Depósito inicial",
	"reference": "Ref123"
}
```

## Ejemplo de JSON para transferencia bancaria
```json
{
	"fromAccountId": 1,
	"toAccountId": 2,
	"amount": 200.0,
	"reference": "Pago a proveedor"
}
```

## Ejemplo de JSON para movimiento de caja
```json
{
	"registerId": 1,
	"amount": 100.0,
	"type": "INCOME", // o "EXPENSE"
	"description": "Venta al contado",
	"reference": "Caja1"
}
```

## Ejemplo de JSON para transferencia de caja
```json
{
	"fromRegisterId": 1,
	"toRegisterId": 2,
	"amount": 50.0,
	"reference": "Reposición caja chica"
}
```

## Validaciones y reglas
- No permite retiros/egresos mayores al saldo disponible.
- Solo tipos de movimiento válidos: DEPOSIT, WITHDRAWAL, TRANSFER (banco); INCOME, EXPENSE, TRANSFER (caja).
- Transferencias: se registran como retiro en origen y depósito/ingreso en destino.

## Reportes
- Suma de saldos bancarios y de caja.
- Movimientos por cuenta, caja o fecha.

## Pruebas rápidas con Postman
1. Inicia sesión y obtén el token JWT.
2. Usa el token en la cabecera `Authorization: Bearer <token>`.
3. Realiza peticiones a los endpoints anteriores.
4. Prueba transferencias y verifica saldos antes y después.
# Módulo: Cuentas por Pagar y Cobrar (CxP y CxC)

Este módulo permite gestionar cuentas por pagar a proveedores y cuentas por cobrar a clientes, registrar pagos y cobros, consultar saldos, vencimientos y reportes.

## Entidades principales
- **AccountPayable**: cuenta por pagar a proveedor.
- **Payment**: pago realizado a proveedor.
- **AccountReceivable**: cuenta por cobrar a cliente.
- **Collection**: cobro recibido de cliente.

## Endpoints principales
### Cuentas por pagar
- `GET /api/accounts/payables` — Lista todas las cuentas por pagar
- `GET /api/accounts/payables/{id}` — Consulta una cuenta por pagar
- `GET /api/accounts/payables/supplier/{supplierId}` — Por proveedor
- `GET /api/accounts/payables/due-date?start=YYYY-MM-DD&end=YYYY-MM-DD` — Por vencimiento
- `GET /api/accounts/payables/pending` — Solo pendientes
- `POST /api/accounts/payables` — Crear cuenta por pagar
- `POST /api/accounts/payables/payment` — Registrar pago
- `GET /api/accounts/payables/{id}/payments` — Pagos de una cuenta
- `GET /api/accounts/payables/payments/date-range?start=YYYY-MM-DD&end=YYYY-MM-DD` — Pagos por fecha
- `GET /api/accounts/payables/total-pending` — Total pendiente

### Cuentas por cobrar
- `GET /api/accounts/receivables` — Lista todas las cuentas por cobrar
- `GET /api/accounts/receivables/{id}` — Consulta una cuenta por cobrar
- `GET /api/accounts/receivables/customer/{customerId}` — Por cliente
- `GET /api/accounts/receivables/due-date?start=YYYY-MM-DD&end=YYYY-MM-DD` — Por vencimiento
- `GET /api/accounts/receivables/pending` — Solo pendientes
- `POST /api/accounts/receivables` — Crear cuenta por cobrar
- `POST /api/accounts/receivables/collection` — Registrar cobro
- `GET /api/accounts/receivables/{id}/collections` — Cobros de una cuenta
- `GET /api/accounts/receivables/collections/date-range?start=YYYY-MM-DD&end=YYYY-MM-DD` — Cobros por fecha
- `GET /api/accounts/receivables/total-pending` — Total pendiente

## Ejemplo de JSON para crear cuenta por pagar
```json
{
	"supplierId": 1,
	"amount": 1000.0,
	"dueDate": "2025-09-30",
	"description": "Compra de insumos"
}
```

## Ejemplo de JSON para registrar pago
```json
{
	"payableId": 1,
	"amount": 500.0,
	"method": "Transferencia",
	"reference": "Pago parcial"
}
```

## Ejemplo de JSON para crear cuenta por cobrar
```json
{
	"customerId": 2,
	"amount": 1500.0,
	"dueDate": "2025-09-25",
	"description": "Venta de productos"
}
```

## Ejemplo de JSON para registrar cobro
```json
{
	"receivableId": 2,
	"amount": 1000.0,
	"method": "Efectivo",
	"reference": "Cobro parcial"
}
```

## Validaciones y reglas
- No permite pagos/cobros mayores al saldo pendiente.
- No permite montos negativos o nulos.
- Marca la cuenta como saldada cuando el saldo llega a cero.
- Permite consultar pagos/cobros por cuenta o por fecha.

## Reportes
- Total pendiente por pagar y por cobrar.
- Listados por proveedor, cliente, vencimiento y estado.

## Pruebas rápidas con Postman
1. Inicia sesión y obtén el token JWT.
2. Usa el token en la cabecera `Authorization: Bearer <token>`.
3. Realiza peticiones a los endpoints anteriores.
4. Prueba pagos/cobros parciales y totales, y consulta los saldos.
# Módulo: Inventario (Inventory)

Este módulo permite gestionar existencias de productos en almacenes, registrar movimientos (entradas, salidas, transferencias), y auditar el historial de inventario.

## Entidades principales
- **Warehouse**: almacén físico.
- **Inventory**: existencias de un producto en un almacén.
- **InventoryMovement**: registro de cada movimiento de inventario (entrada, salida, transferencia).

## Endpoints principales
- `GET /api/inventory` — Lista todo el inventario
- `GET /api/inventory/warehouse/{warehouseId}` — Existencias por almacén
- `GET /api/inventory/product/{productId}` — Existencias por producto
- `GET /api/inventory/warehouses` — Lista de almacenes
- `POST /api/inventory/warehouses` — Crear almacén
- `DELETE /api/inventory/warehouses/{id}` — Eliminar almacén
- `GET /api/inventory/movements` — Todos los movimientos
- `GET /api/inventory/movements/date-range?start=YYYY-MM-DD&end=YYYY-MM-DD` — Movimientos por fecha
- `GET /api/inventory/movements/product/{productId}` — Movimientos por producto
- `GET /api/inventory/movements/warehouse/{warehouseId}` — Movimientos por almacén
- `POST /api/inventory/movements` — Registrar entrada/salida
- `POST /api/inventory/movements/transfer` — Transferir stock entre almacenes
- `GET /api/inventory/audit?...` — Auditoría de movimientos (por producto, almacén o fecha)

## Ejemplo de JSON para registrar movimiento
```json
{
	"warehouseId": 1,
	"productId": 2,
	"quantity": 10,
	"type": "IN", // o "OUT"
	"reference": "Compra #123"
}
```

## Ejemplo de JSON para transferencia
```json
{
	"fromWarehouseId": 1,
	"toWarehouseId": 2,
	"productId": 2,
	"quantity": 5,
	"reference": "Traslado entre bodegas"
}
```

## Validaciones y reglas
- No permite stock negativo (salida solo si hay suficiente existencia).
- Solo tipos de movimiento válidos: IN (entrada), OUT (salida), TRANSFER (transferencia).
- Transferencias: se registran como salida en origen y entrada en destino.
- Auditoría: historial completo de movimientos por producto, almacén o fecha.

## Reportes y auditoría
- Consultar movimientos por rango de fechas, producto o almacén.
- Consultar existencias actuales por producto y almacén.

## Pruebas rápidas con Postman
1. Inicia sesión y obtén el token JWT.
2. Usa el token en la cabecera `Authorization: Bearer <token>`.
3. Realiza peticiones a los endpoints anteriores.
4. Prueba transferencias y verifica existencias antes y después.
# Módulo: Compras (Purchase)

Este módulo permite gestionar las compras a proveedores. Incluye:
- Registro de compras y sus líneas (productos comprados)
- Relación con proveedores y productos
- Validaciones de datos (proveedor, productos, cantidades, precios)
- Reportes de compras por fecha y por proveedor

## Endpoints principales

- `GET /api/purchases` — Lista todas las compras
- `GET /api/purchases/{id}` — Consulta una compra por ID
- `POST /api/purchases` — Crea una nueva compra
- `PUT /api/purchases/{id}` — Actualiza una compra existente
- `DELETE /api/purchases/{id}` — Elimina una compra

## Ejemplo de JSON para crear una compra
```json
{
	"supplier": { "id": 1 },
	"date": "2025-09-11",
	"lines": [
		{ "product": { "id": 1 }, "quantity": 10, "price": 50.0 },
		{ "product": { "id": 2 }, "quantity": 5, "price": 100.0 }
	]
}
```

## Ejemplo de JSON para actualizar una compra
```json
{
	"supplier": { "id": 2 },
	"date": "2025-09-12",
	"lines": [
		{ "product": { "id": 1 }, "quantity": 20, "price": 48.0 }
	]
}
```

## Validaciones
- El proveedor debe existir y ser válido.
- Debe haber al menos una línea de producto.
- Cada línea debe tener producto válido, cantidad y precio mayor a cero.
- Si alguna validación falla, la API responde con un mensaje de error claro.

## Reportes de compras

### Compras por rango de fechas
`GET /api/purchases/report/date-range?start=2025-09-01&end=2025-09-11`
Devuelve todas las compras realizadas entre las fechas indicadas (inclusive).

### Total de compras por proveedor
`GET /api/purchases/report/total-by-supplier/{supplierId}`
Devuelve el total comprado a un proveedor específico.

## Pruebas rápidas con Postman
1. Inicia sesión y obtén el token JWT.
2. Usa el token en la cabecera `Authorization: Bearer <token>`.
3. Realiza peticiones a los endpoints anteriores.
4. Para reportes, usa los endpoints de reportes con los parámetros deseados.
# Reportes de compras

## Compras por rango de fechas
`GET /api/purchases/report/date-range?start=2025-09-01&end=2025-09-11`
Devuelve todas las compras realizadas entre las fechas indicadas (inclusive).

## Total de compras por proveedor
`GET /api/purchases/report/total-by-supplier/{supplierId}`
Devuelve el total comprado a un proveedor específico.
# Módulo: Compras (Purchase)

Este módulo permite gestionar las compras a proveedores. Incluye operaciones para crear, consultar, actualizar y eliminar compras, así como sus líneas (productos comprados).

## Endpoints principales

- `GET /api/purchases` — Lista todas las compras
- `GET /api/purchases/{id}` — Consulta una compra por ID
- `POST /api/purchases` — Crea una nueva compra
- `PUT /api/purchases/{id}` — Actualiza una compra existente
- `DELETE /api/purchases/{id}` — Elimina una compra

### Ejemplo de JSON para crear una compra
```json
{
	"supplier": { "id": 1 },
	"date": "2025-09-11",
	"lines": [
		{ "product": { "id": 1 }, "quantity": 10, "price": 50.0 },
		{ "product": { "id": 2 }, "quantity": 5, "price": 100.0 }
	]
}
```

### Pruebas rápidas con Postman
1. Inicia sesión y obtén el token JWT.
2. Usa el token en la cabecera `Authorization: Bearer <token>`.
3. Realiza peticiones a los endpoints anteriores.
### 8. Registro de asientos contables (Journal Entry)

Puedes gestionar asientos contables usando la API en `/api/journal-entries`.

**Crear asiento:**
- Método: POST
- URL: http://localhost:8080/api/journal-entries
- Headers: Authorization: Bearer TU_TOKEN
- Body (raw, JSON):
	```json
	{
		"entryNumber": "JE-0001",
		"date": "2025-09-09T00:00:00.000+00:00",
		"description": "Compra de mercadería",
		"status": "Draft",
		"createdBy": {"id": 2},
		"lines": [
			{
				"account": {"id": 1},
				"description": "Inventario",
				"debit": 1000.00,
				"credit": 0.00,
				"lineNumber": 1
			},
			{
				"account": {"id": 2},
				"description": "Proveedores",
				"debit": 0.00,
				"credit": 1000.00,
				"lineNumber": 2
			}
		]
	}
	```
	- Si la suma de débitos y créditos no cuadra, la API responderá con error.

**Listar asientos:**
- Método: GET
- URL: http://localhost:8080/api/journal-entries
- Headers: Authorization: Bearer TU_TOKEN

**Ver asiento por ID:**
- Método: GET
- URL: http://localhost:8080/api/journal-entries/ID_ASIENTO
- Headers: Authorization: Bearer TU_TOKEN

**Eliminar asiento:**
- Método: DELETE
- URL: http://localhost:8080/api/journal-entries/ID_ASIENTO
- Headers: Authorization: Bearer TU_TOKEN
### 7. Catálogo de cuentas contables (Account)

Puedes gestionar cuentas contables usando la API en `/api/accounts`.

**Crear cuenta:**
- Método: POST
- URL: http://localhost:8080/api/accounts
- Headers: Authorization: Bearer TU_TOKEN
- Body (raw, JSON):
	```json
	{
		"code": "1001",
		"name": "Caja General",
		"parent": null, // o {"id": ID_PADRE} para cuentas hijas
		"level": 1,
		"type": "Asset",
		"isDetail": true,
		"isActive": true,
		"nature": "Debit"
	}
	```

**Listar cuentas:**
- Método: GET
- URL: http://localhost:8080/api/accounts
- Headers: Authorization: Bearer TU_TOKEN

**Editar cuenta:**
- Método: PUT
- URL: http://localhost:8080/api/accounts/ID_CUENTA
- Headers: Authorization: Bearer TU_TOKEN
- Body: igual que en crear, pero con los datos actualizados

**Eliminar cuenta:**
- Método: DELETE
- URL: http://localhost:8080/api/accounts/ID_CUENTA
- Headers: Authorization: Bearer TU_TOKEN

Puedes crear jerarquía de cuentas usando el campo `parent` con el ID de la cuenta agrupadora.
### 6. Asignar roles a un usuario

1. Obtén el ID del usuario y los IDs de los roles que quieres asignar (puedes listarlos con GET /api/users y GET /api/roles).
2. Haz un PUT a `http://localhost:8080/api/users/{id}/roles` (reemplaza {id} por el ID del usuario):
	 - Headers: Authorization: Bearer TU_TOKEN
	 - Body (raw, JSON):
		 ```json
		 [1, 2]
		 ```
		 (donde 1 y 2 son los IDs de los roles a asignar)
3. El usuario quedará con los roles indicados.

# PYMES
Proyecto que busca consolidar procesos contables de PYMES

## Guía para probar el proyecto (configuración y autenticación JWT)

### 1. Crear la base de datos PostgreSQL

1. Instala PostgreSQL y pgAdmin si no lo tienes.
2. Crea una base de datos llamada `pymes` (o el nombre que prefieras).
3. Ejecuta el script SQL de creación de tablas (ver carpeta docs o pide el archivo a un compañero).
4. El usuario por defecto es `postgres` y el puerto suele ser `5432`.
5. Si usas otro nombre de base de datos, usuario o contraseña, edita el archivo `src/main/resources/application.properties`:
	```properties
	spring.datasource.url=jdbc:postgresql://localhost:5432/pymes
	spring.datasource.username=postgres
	spring.datasource.password=TU_CONTRASEÑA
	```

### 2. Ejecutar el proyecto

1. Abre una terminal en la carpeta `demo`.
2. Ejecuta:
	```powershell
	.\mvnw spring-boot:run
	```
	o si tienes Maven global:
	```powershell
	mvn spring-boot:run
	```

### 3. Crear el primer usuario (solo la primera vez)

1. Haz un POST a `http://localhost:8080/api/users` con Postman:
	- Body (raw, JSON):
	  ```json
	  {
		 "username": "admin",
		 "password": "admin123",
		 "fullName": "Administrador",
		 "email": "admin@example.com",
		 "isActive": true,
		 "roles": []
	  }
	  ```
2. El usuario se creará con la contraseña encriptada automáticamente.

### 4. Login y obtención de token JWT

1. Haz un POST a `http://localhost:8080/api/auth/login`:
	- Body (raw, JSON):
	  ```json
	  {
		 "username": "admin",
		 "password": "admin123"
	  }
	  ```
2. Copia el valor del campo `token` de la respuesta.

### 5. Acceso a endpoints protegidos

1. En cualquier petición protegida (por ejemplo, GET `http://localhost:8080/api/users`), agrega el header:
	- Key: `Authorization`
	- Value: `Bearer TU_TOKEN`

### Notas
- Si cambias el nombre de la base de datos, usuario o contraseña, actualiza `application.properties`.
- Si necesitas crear más usuarios, hazlo autenticado con un token válido.
- Si tienes dudas, revisa este README o consulta a un compañero.


## Avances recientes

- Se crearon las entidades User y Role para la gestión de usuarios y perfiles.
- Se implementaron los repositorios UserRepository y RoleRepository para acceso a base de datos.
- Se agregaron los servicios UserService y RoleService para la lógica de negocio de usuarios y roles.

- Se crearon los controladores REST UserController y RoleController para exponer la gestión de usuarios y roles vía API.

- Se agregaron dependencias de Spring Security y JWT (jjwt) para implementar autenticación basada en tokens.

- Se implementó autenticación JWT:
	- Clases JwtUtil y JwtAuthenticationFilter para generación y validación de tokens.
	- Configuración de seguridad en SecurityConfig.
	- Servicio CustomUserDetailsService para integración con usuarios de la base de datos.
	- Endpoint /api/auth/login para obtener el token JWT.
