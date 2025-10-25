@echo off
REM ======================================================
REM CONFIGURADOR INICIAL - CREAR USUARIO ADMINISTRADOR
REM ======================================================
echo.
echo ======================================================
echo     CONFIGURACIÓN INICIAL DEL SISTEMA ERP
echo ======================================================
echo.
echo Este script creará el usuario administrador inicial.
echo.
echo REQUISITOS:
echo - El backend debe estar ejecutándose en puerto 8082
echo - PowerShell debe estar disponible
echo.

REM Verificar si el backend está corriendo
echo Verificando si el backend está corriendo...
powershell -Command "try { Invoke-RestMethod -Uri 'http://localhost:8082/actuator/health' -TimeoutSec 5 } catch { exit 1 }" >nul 2>&1

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: El backend no está corriendo en puerto 8082.
    echo.
    echo Para iniciar el backend:
    echo   1. Ejecuta: start-backend.bat
    echo   2. Espera a ver "Started DemoApplication"
    echo   3. Ejecuta este script nuevamente
    echo.
    pause
    exit /b 1
)

echo ✓ Backend encontrado en puerto 8082
echo.
echo Creando usuario administrador...

REM Crear usuario administrador
powershell -Command "try { $response = Invoke-RestMethod -Uri 'http://localhost:8082/api/users' -Method POST -ContentType 'application/json' -Body '{\"username\":\"admin\",\"password\":\"admin123\",\"fullName\":\"Administrador\",\"email\":\"admin@example.com\",\"isActive\":true,\"roles\":[]}'; Write-Host '✓ Usuario creado exitosamente'; Write-Host ('ID: ' + $response.id); Write-Host ('Nombre: ' + $response.fullName) } catch { Write-Host 'Error al crear usuario:' $_.Exception.Message; exit 1 }"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ======================================================
    echo         ¡CONFIGURACIÓN COMPLETADA!
    echo ======================================================
    echo.
    echo Usuario administrador creado:
    echo   Usuario: admin
    echo   Contraseña: admin123
    echo.
    echo Ahora puedes:
    echo   1. Abrir http://localhost:4200 (frontend)
    echo   2. Hacer login con las credenciales de arriba
    echo.
    echo O probar el login desde PowerShell:
    echo   Invoke-RestMethod -Uri "http://localhost:8082/api/auth/login" -Method POST -ContentType "application/json" -Body '{"username":"admin","password":"admin123"}'
    echo.
) else (
    echo.
    echo ERROR: No se pudo crear el usuario administrador.
    echo Esto puede deberse a que:
    echo   - El usuario ya existe
    echo   - Hay un problema con el endpoint /api/users
    echo   - El backend no está configurado correctamente
    echo.
    echo Revisa los logs del backend para más información.
    echo.
)

pause