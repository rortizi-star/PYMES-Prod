param(
  [string]$BackendUrl = 'http://localhost:8082',
  [int]$Port = 4200,
  [switch]$Serve
)

Write-Host "Building erp-frontend (output -> erp-frontend/dist)" -ForegroundColor Cyan
Push-Location -Path (Join-Path $PSScriptRoot '..')
try{
  if (Test-Path node_modules -PathType Container) {
    Write-Host "node_modules exists, skipping install" -ForegroundColor Yellow
  } else {
    Write-Host "Running npm ci..." -ForegroundColor Green
    npm ci
  }

  Write-Host "Running ng build --output-path=dist" -ForegroundColor Green
  npm run build -- --output-path=dist

  $config = "window.__BACKEND_URL__='$BackendUrl'"
  $distDir = Join-Path (Get-Location) 'dist'
  if (-not (Test-Path $distDir)) { throw "dist folder not found after build" }
  $configPath = Join-Path $distDir 'config.js'
  Write-Host "Writing runtime config to $configPath" -ForegroundColor Green
  $config | Out-File -Encoding utf8 -FilePath $configPath

  if ($Serve) {
    Write-Host "Serving $distDir on port $Port (press Ctrl+C to stop)" -ForegroundColor Cyan
    # use http-server if available via npx
    npx http-server $distDir -p $Port -c-1
  } else {
    Write-Host "Build complete. To serve locally run this script with -Serve switch or upload dist to your static host." -ForegroundColor Green
  }
} finally {
  Pop-Location
}
