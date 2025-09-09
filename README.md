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
