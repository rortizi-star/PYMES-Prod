# 🏢 Sistema ERP para PYMES - Guía de Instalación

## 📋 Descripción
Sistema ERP completo para pequeñas y medianas empresas con:
- **Backend**: Spring Boot 3.5.5 + Java 21 + H2 Database
- **Frontend**: Angular 17 + Angular Material
- **Autenticación**: JWT + Spring Security

## 🛠️ Requisitos del Sistema
- **Sistema Operativo**: Windows 10/11
- **RAM**: Mínimo 8GB recomendado
- **Espacio en disco**: 2GB libres
- **Conexión a Internet**: Para descargar dependencias

## 🚀 Instalación Automática (Recomendada)

### Opción 1: Instalación Completa Automática

1. **Abrir PowerShell como Administrador**:
   - Presiona `Windows + X`
   - Selecciona "Windows PowerShell (Administrador)"
   - O busca "PowerShell" → Clic derecho → "Ejecutar como administrador"

2. **Navegar al directorio del proyecto**:
   ```powershell
   cd "C:\Users\Roberto\Documents\PYMES"
   ```

3. **Habilitar ejecución de scripts** (si es necesario):
   ```powershell
   Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
   ```

4. **Ejecutar el instalador**:
   ```powershell
   .\install-dependencies.ps1
   ```

### Opciones Avanzadas del Instalador

```powershell
# Ver ayuda
.\install-dependencies.ps1 -Help

# Omitir instalación de Java (si ya tienes Java 21)
.\install-dependencies.ps1 -SkipJava

# Omitir instalación de Node.js (si ya tienes Node 18+)
.\install-dependencies.ps1 -SkipNode

# Solo instalar herramientas, sin dependencias del proyecto
.\install-dependencies.ps1 -SkipDependencies
```

## 🔧 Instalación Manual (Si la automática falla)

### Paso 1: Instalar Java 21
```powershell
# Con Chocolatey (recomendado)
choco install openjdk21 -y

# O descargar desde: https://adoptium.net/
```

### Paso 2: Instalar Node.js
```powershell
# Con Chocolatey
choco install nodejs-lts -y

# O descargar desde: https://nodejs.org/
```

### Paso 3: Instalar Angular CLI
```powershell
npm install -g @angular/cli@17
```

### Paso 4: Instalar dependencias del Backend
```powershell
cd demo
.\mvnw.cmd clean compile -DskipTests
```

### Paso 5: Instalar dependencias del Frontend
```powershell
cd ../erp-frontend
npm install
```

## ▶️ Cómo Ejecutar el Sistema

### Opción 1: Inicio Automático (Todo el Sistema)
```batch
start-erp.bat
```
Esto iniciará automáticamente el backend y luego el frontend.

### Opción 2: Inicio Manual por Separado

#### Iniciar Backend:
```batch
start-backend.bat
```
O manualmente:
```powershell
cd demo
.\mvnw.cmd spring-boot:run
```

#### Iniciar Frontend (en otra terminal):
```batch
start-frontend.bat
```
O manualmente:
```powershell
cd erp-frontend
ng serve --port 4200 --open
```

## 🌐 URLs del Sistema

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **Frontend** | http://localhost:4200 | Interfaz principal del ERP |
| **Backend API** | http://localhost:8082 | API REST del sistema |
| **Base de Datos H2** | http://localhost:8082/h2-console | Consola de administración de BD |

### Configuración de H2 Console:
- **JDBC URL**: `jdbc:h2:mem:pymes`
- **Usuario**: `postgres` 
- **Contraseña**: *(en blanco)*

## 🔐 Credenciales por Defecto

Para crear el usuario administrador inicial:

```powershell
# Con el backend corriendo, ejecutar:
Invoke-RestMethod -Uri "http://localhost:8082/api/users" -Method POST -ContentType "application/json" -Body '{"username":"admin","password":"admin123","fullName":"Administrador","email":"admin@example.com","isActive":true,"roles":[]}'
```

**Credenciales de login**:
- **Usuario**: `admin`
- **Contraseña**: `admin123`

## 🔍 Solución de Problemas Comunes

### ❌ Error: "No se puede ejecutar scripts"
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### ❌ Error: "Java no encontrado" 
- Verifica que Java 21 esté instalado: `java -version`
- Reinicia PowerShell después de instalar Java
- Verifica que JAVA_HOME esté configurado

### ❌ Error: "Puerto 8082 ya en uso"
```powershell
# Encontrar y terminar proceso en puerto 8082
netstat -ano | findstr :8082
taskkill /PID [PID_NUMBER] /F
```

### ❌ Error: "Puerto 4200 ya en uso"
```powershell
# Usar puerto diferente para Angular
ng serve --port 4201
```

### ❌ Error: "npm install falla"
```powershell
# Limpiar cache de npm
npm cache clean --force
cd erp-frontend
rm -rf node_modules package-lock.json
npm install
```

### ❌ Error: "Maven no funciona"
```powershell
# Verificar que estés en la carpeta correcta
cd demo
# Dar permisos al wrapper (en algunos casos)
chmod +x mvnw.cmd
```

## 📁 Estructura del Proyecto

```
PYMES/
├── demo/                          # Backend (Spring Boot)
│   ├── src/main/java/            # Código fuente Java
│   ├── src/main/resources/       # Configuraciones
│   ├── pom.xml                   # Dependencias Maven
│   └── mvnw.cmd                  # Maven Wrapper
├── erp-frontend/                 # Frontend (Angular)
│   ├── src/app/                  # Código fuente Angular
│   ├── package.json              # Dependencias npm
│   └── angular.json              # Configuración Angular
├── install-dependencies.ps1      # Instalador automático
├── start-backend.bat            # Iniciar solo backend
├── start-frontend.bat           # Iniciar solo frontend
└── start-erp.bat               # Iniciar sistema completo
```

## 🛡️ Tecnologías Utilizadas

### Backend
- **Spring Boot 3.5.5** - Framework principal
- **Java 21** - Lenguaje de programación
- **Spring Security** - Autenticación y autorización
- **JWT** - Tokens de autenticación
- **Spring Data JPA** - ORM para base de datos
- **H2 Database** - Base de datos en memoria (desarrollo)
- **PostgreSQL** - Base de datos de producción
- **Maven** - Gestión de dependencias

### Frontend
- **Angular 17** - Framework de frontend
- **Angular Material** - Componentes UI
- **TypeScript** - Lenguaje principal
- **RxJS** - Programación reactiva
- **Chart.js** - Gráficos y reportes
- **jsPDF** - Generación de PDFs

## 📞 Soporte

Si encuentras problemas durante la instalación:

1. Verifica que tengas permisos de administrador
2. Asegúrate de tener conexión a Internet estable
3. Revisa los logs de error en la consola
4. Intenta la instalación manual paso a paso

## 🔄 Actualizaciones

Para actualizar el sistema:

```powershell
# Actualizar backend
cd demo
.\mvnw.cmd clean compile

# Actualizar frontend  
cd ../erp-frontend
npm update
```

## 📝 Notas Importantes

- El primer inicio puede tardar varios minutos mientras se descargan las dependencias
- La base de datos H2 es temporal y se reinicia cada vez que paras el backend
- Para producción, configura PostgreSQL en `application.properties`
- Los puertos 4200 y 8082 deben estar libres
- Se recomienda tener al menos 4GB de RAM libres durante el desarrollo