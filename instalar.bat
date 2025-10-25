@echo off
REM ======================================================
REM INSTALADOR SIMPLE - SISTEMA ERP PYMES
REM ======================================================
echo.
echo ======================================================
echo     INSTALADOR SIMPLE - SISTEMA ERP PYMES
echo ======================================================
echo.

REM Verificar si PowerShell está disponible
powershell -Command "Write-Host 'Verificando PowerShell...'" >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: PowerShell no está disponible en este sistema.
    echo Por favor, actualiza tu sistema Windows.
    pause
    exit /b 1
)

echo Iniciando instalación automática...
echo.
echo IMPORTANTE: Este proceso puede tomar 10-30 minutos dependiendo de tu conexión.
echo.
set /p confirm="¿Continuar con la instalación? (S/N): "
if /i "%confirm%" NEQ "S" (
    echo Instalación cancelada.
    pause
    exit /b 0
)

echo.
echo Ejecutando el instalador de PowerShell...
echo Si aparece un error de permisos, ejecuta este archivo como Administrador.
echo.

REM Ejecutar el script de PowerShell
powershell -ExecutionPolicy Bypass -File "install-dependencies.ps1"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ======================================================
    echo           ¡INSTALACIÓN COMPLETADA!
    echo ======================================================
    echo.
    echo Para iniciar el sistema, ejecuta:
    echo   start-erp.bat      [Sistema completo]
    echo   start-backend.bat  [Solo backend]
    echo   start-frontend.bat [Solo frontend]
    echo.
) else (
    echo.
    echo ERROR: La instalación falló.
    echo Revisa el archivo INSTALACION.md para instrucciones manuales.
    echo.
)

pause