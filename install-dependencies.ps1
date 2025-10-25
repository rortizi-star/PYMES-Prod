# ======================================================
# INSTALADOR AUTOMATIZADO - SISTEMA ERP PYMES
# ======================================================
# Este script instala todas las dependencias necesarias
# para el sistema ERP de PYMES (Frontend + Backend)
# ======================================================

param(
    [switch]$SkipJava,
    [switch]$SkipNode,
    [switch]$SkipDependencies,
    [switch]$Help
)

if ($Help) {
    Write-Host "=== INSTALADOR ERP PYMES ===" -ForegroundColor Green
    Write-Host "Opciones disponibles:"
    Write-Host "  -SkipJava         : Omite instalación de Java"
    Write-Host "  -SkipNode         : Omite instalación de Node.js"
    Write-Host "  -SkipDependencies : Omite instalación de dependencias"
    Write-Host "  -Help             : Muestra esta ayuda"
    Write-Host ""
    Write-Host "Ejemplo: .\install-dependencies.ps1 -SkipJava"
    exit
}

# Configuración de colores
$ErrorColor = "Red"
$SuccessColor = "Green"
$InfoColor = "Cyan"
$WarningColor = "Yellow"

function Write-Step {
    param($Message, $Color = $InfoColor)
    Write-Host "`n==> $Message" -ForegroundColor $Color
}

function Write-Error-Exit {
    param($Message)
    Write-Host "ERROR: $Message" -ForegroundColor $ErrorColor
    Write-Host "Presiona cualquier tecla para salir..."
    Read-Host
    exit 1
}

function Test-Administrator {
    $currentUser = [Security.Principal.WindowsIdentity]::GetCurrent()
    $principal = New-Object Security.Principal.WindowsPrincipal($currentUser)
    return $principal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
}

function Test-Command {
    param($Command)
    try {
        Get-Command $Command -ErrorAction Stop | Out-Null
        return $true
    }
    catch {
        return $false
    }
}

function Install-Chocolatey {
    Write-Step "Verificando Chocolatey..."
    if (Test-Command "choco") {
        Write-Host "✓ Chocolatey ya está instalado" -ForegroundColor $SuccessColor
        return
    }

    Write-Step "Instalando Chocolatey..." $WarningColor
    Set-ExecutionPolicy Bypass -Scope Process -Force
    [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
    try {
        Invoke-Expression ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
        Write-Host "✓ Chocolatey instalado correctamente" -ForegroundColor $SuccessColor
    }
    catch {
        Write-Error-Exit "No se pudo instalar Chocolatey. Verifica tu conexión a Internet."
    }
}

function Install-Java {
    if ($SkipJava) {
        Write-Step "Omitiendo instalación de Java (parámetro -SkipJava)" $WarningColor
        return
    }

    Write-Step "Verificando Java 21..."
    try {
        $javaVersion = java -version 2>&1 | Select-String "version" | ForEach-Object { $_.ToString() }
        if ($javaVersion -match "21\.") {
            Write-Host "✓ Java 21 ya está instalado: $javaVersion" -ForegroundColor $SuccessColor
            return
        }
    }
    catch {
        Write-Host "Java no encontrado" -ForegroundColor $WarningColor
    }

    Write-Step "Instalando OpenJDK 21..." $WarningColor
    try {
        choco install openjdk21 -y
        Write-Host "✓ Java 21 instalado correctamente" -ForegroundColor $SuccessColor
        
        # Actualizar PATH
        $env:Path = [System.Environment]::GetEnvironmentVariable("Path", "Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path", "User")
        
        Write-Host "NOTA: Es posible que necesites reiniciar PowerShell para que Java esté disponible." -ForegroundColor $WarningColor
    }
    catch {
        Write-Error-Exit "Error al instalar Java 21"
    }
}

function Install-NodeJS {
    if ($SkipNode) {
        Write-Step "Omitiendo instalación de Node.js (parámetro -SkipNode)" $WarningColor
        return
    }

    Write-Step "Verificando Node.js..."
    try {
        $nodeVersion = node --version 2>$null
        if ($nodeVersion -match "v(\d+)\.") {
            $majorVersion = [int]$matches[1]
            if ($majorVersion -ge 18) {
                Write-Host "✓ Node.js ya está instalado: $nodeVersion" -ForegroundColor $SuccessColor
                return
            }
        }
    }
    catch {
        Write-Host "Node.js no encontrado" -ForegroundColor $WarningColor
    }

    Write-Step "Instalando Node.js LTS..." $WarningColor
    try {
        choco install nodejs-lts -y
        Write-Host "✓ Node.js instalado correctamente" -ForegroundColor $SuccessColor
        
        # Actualizar PATH
        $env:Path = [System.Environment]::GetEnvironmentVariable("Path", "Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path", "User")
    }
    catch {
        Write-Error-Exit "Error al instalar Node.js"
    }
}

function Install-Angular-CLI {
    Write-Step "Verificando Angular CLI..."
    try {
        $ngVersion = ng version --skip-git 2>$null
        if ($ngVersion -match "Angular CLI") {
            Write-Host "✓ Angular CLI ya está instalado" -ForegroundColor $SuccessColor
            return
        }
    }
    catch {
        Write-Host "Angular CLI no encontrado" -ForegroundColor $WarningColor
    }

    Write-Step "Instalando Angular CLI..." $WarningColor
    try {
        npm install -g @angular/cli@17
        Write-Host "✓ Angular CLI instalado correctamente" -ForegroundColor $SuccessColor
    }
    catch {
        Write-Error-Exit "Error al instalar Angular CLI"
    }
}

function Install-Backend-Dependencies {
    Write-Step "Instalando dependencias del Backend (Spring Boot)..."
    
    $backendPath = ".\demo"
    if (-not (Test-Path $backendPath)) {
        Write-Error-Exit "No se encontró la carpeta del backend: $backendPath"
    }

    Push-Location $backendPath
    try {
        Write-Host "Ejecutando Maven Wrapper..." -ForegroundColor $InfoColor
        .\mvnw.cmd clean compile -DskipTests
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✓ Dependencias del backend instaladas correctamente" -ForegroundColor $SuccessColor
        } else {
            throw "Maven falló con código $LASTEXITCODE"
        }
    }
    catch {
        Write-Error-Exit "Error al instalar dependencias del backend: $($_.Exception.Message)"
    }
    finally {
        Pop-Location
    }
}

function Install-Frontend-Dependencies {
    Write-Step "Instalando dependencias del Frontend (Angular)..."
    
    $frontendPath = ".\erp-frontend"
    if (-not (Test-Path $frontendPath)) {
        Write-Error-Exit "No se encontró la carpeta del frontend: $frontendPath"
    }

    Push-Location $frontendPath
    try {
        Write-Host "Ejecutando npm install..." -ForegroundColor $InfoColor
        npm install
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✓ Dependencias del frontend instaladas correctamente" -ForegroundColor $SuccessColor
        } else {
            throw "npm install falló con código $LASTEXITCODE"
        }
    }
    catch {
        Write-Error-Exit "Error al instalar dependencias del frontend: $($_.Exception.Message)"
    }
    finally {
        Pop-Location
    }
}

function Create-StartupScripts {
    Write-Step "Creando scripts de inicio..."
    
    # Script para iniciar backend
    $backendScript = @"
@echo off
echo Iniciando Backend (Spring Boot)...
cd /d "%~dp0demo"
mvnw.cmd spring-boot:run
pause
"@
    $backendScript | Out-File -FilePath ".\start-backend.bat" -Encoding ASCII
    
    # Script para iniciar frontend
    $frontendScript = @"
@echo off
echo Iniciando Frontend (Angular)...
cd /d "%~dp0erp-frontend"
ng serve --port 4200 --open
pause
"@
    $frontendScript | Out-File -FilePath ".\start-frontend.bat" -Encoding ASCII
    
    # Script para iniciar ambos
    $fullScript = @"
@echo off
echo Iniciando Sistema ERP Completo...
echo.
echo 1. Iniciando Backend en puerto 8082...
start "Backend" cmd /c "cd /d "%~dp0demo" && mvnw.cmd spring-boot:run"

echo.
echo 2. Esperando 30 segundos para que el backend inicie...
timeout /t 30 /nobreak

echo.
echo 3. Iniciando Frontend en puerto 4200...
cd /d "%~dp0erp-frontend"
ng serve --port 4200 --open
"@
    $fullScript | Out-File -FilePath ".\start-erp.bat" -Encoding ASCII
    
    Write-Host "✓ Scripts de inicio creados:" -ForegroundColor $SuccessColor
    Write-Host "  - start-backend.bat  (Solo backend)" -ForegroundColor $InfoColor
    Write-Host "  - start-frontend.bat (Solo frontend)" -ForegroundColor $InfoColor
    Write-Host "  - start-erp.bat      (Sistema completo)" -ForegroundColor $InfoColor
}

# ======================================================
# INICIO DEL SCRIPT PRINCIPAL
# ======================================================

Clear-Host
Write-Host "======================================================" -ForegroundColor Green
Write-Host "    INSTALADOR AUTOMATIZADO - SISTEMA ERP PYMES     " -ForegroundColor Green
Write-Host "======================================================" -ForegroundColor Green
Write-Host ""

# Verificar permisos de administrador
if (-not (Test-Administrator)) {
    Write-Error-Exit "Este script debe ejecutarse como Administrador. Haz clic derecho -> 'Ejecutar como administrador'"
}

try {
    # Instalación de herramientas base
    Install-Chocolatey
    Install-Java
    Install-NodeJS
    Install-Angular-CLI

    if (-not $SkipDependencies) {
        # Instalación de dependencias del proyecto
        Install-Backend-Dependencies
        Install-Frontend-Dependencies
    }

    # Crear scripts de inicio
    Create-StartupScripts

    # Resumen final
    Write-Host "`n======================================================" -ForegroundColor Green
    Write-Host "             ¡INSTALACIÓN COMPLETADA!               " -ForegroundColor Green
    Write-Host "======================================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "El sistema ERP está listo para usar. Puedes iniciarlo con:" -ForegroundColor $InfoColor
    Write-Host ""
    Write-Host "• start-erp.bat      - Inicia todo el sistema automáticamente" -ForegroundColor $SuccessColor
    Write-Host "• start-backend.bat  - Solo el backend (Spring Boot)" -ForegroundColor $InfoColor
    Write-Host "• start-frontend.bat - Solo el frontend (Angular)" -ForegroundColor $InfoColor
    Write-Host ""
    Write-Host "URLs del sistema:" -ForegroundColor $InfoColor
    Write-Host "• Frontend: http://localhost:4200" -ForegroundColor $WarningColor
    Write-Host "• Backend:  http://localhost:8082" -ForegroundColor $WarningColor
    Write-Host "• Base de datos H2: http://localhost:8082/h2-console" -ForegroundColor $WarningColor
    Write-Host ""
    Write-Host "Credenciales por defecto:" -ForegroundColor $InfoColor
    Write-Host "• Usuario: admin" -ForegroundColor $WarningColor
    Write-Host "• Contraseña: admin123" -ForegroundColor $WarningColor
    Write-Host ""
    
}
catch {
    Write-Error-Exit "Error durante la instalación: $($_.Exception.Message)"
}

Write-Host "Presiona cualquier tecla para salir..." -ForegroundColor $InfoColor
Read-Host