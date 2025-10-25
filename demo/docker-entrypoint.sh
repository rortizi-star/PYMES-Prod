#!/bin/sh
# Entrypoint que asegura que NODE_NAME esté definido en tiempo de ejecución.
# - En Render se pueden exponer variables de metadata como RENDER_INSTANCE_ID o RENDER_SERVICE_ID.
# - Si no hay variable disponible, usamos el hostname del contenedor.

set -e

# Preferir NODE_NAME ya definido
if [ -z "${NODE_NAME}" ]; then
  # Intentar variables comunes que Render podría suministrar
  if [ -n "${RENDER_INSTANCE_ID}" ]; then
    NODE_NAME="${RENDER_INSTANCE_ID}"
  elif [ -n "${RENDER_SERVICE_ID}" ]; then
    NODE_NAME="${RENDER_SERVICE_ID}"
  elif [ -n "${RENDER_SERVICE_NAME}" ]; then
    NODE_NAME="${RENDER_SERVICE_NAME}"
  else
    NODE_NAME="$(hostname)"
  fi
fi

export NODE_NAME

echo "[entrypoint] NODE_NAME=$NODE_NAME"

exec java -Dserver.port="${PORT:-8082}" -Djava.security.egd=file:/dev/./urandom ${SPRING_JAVA_OPTIONS} -jar /app/app.jar
