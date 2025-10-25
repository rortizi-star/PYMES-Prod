# Frontends de prueba (demo/frontends)

En esta carpeta hay dos frontends estáticos para probar el backend desplegado en Render:

- `frontend-a/index.html` — UI simple: envía una sola petición POST a `/api/audit/log` y muestra la respuesta.
- `frontend-b/index.html` — ráfaga: envía N peticiones en secuencia y lista la respuesta de cada una (útil para observar alternancia entre nodos).

Cómo usarlos
1. Edita los archivos HTML y reemplaza la cadena `%BACKEND_URL%` por la URL pública de tu servicio backend en Render (por ejemplo `https://pymes-backend.onrender.com`).
2. Correr localmente (modo rápido):

```powershell
# desde la carpeta demo/frontends/frontend-a o frontend-b
python -m http.server 8000
# o con Node (si tienes http-server instalado)
npx http-server . -p 8000
```

3. Abre en el navegador `http://localhost:8000` y prueba enviar peticiones.

Desplegar en Render (opcional)
- Puedes crear dos Static Sites en Render apuntando a `demo/frontends/frontend-a` y `demo/frontends/frontend-b` respectivamente o usar la sección `staticSites` en `render.yaml` y `render.yml` si quieres alojarlos desde el mismo repo.

Notas
- Asegúrate de que el backend permita CORS desde los orígenes donde hospedarás los frontends (si despliegas en Render, la URL será del tipo `https://<name>.onrender.com`).
- Estos frontends son solamente utilidades de prueba; no contienen autenticación. Si tu endpoint requiere JWT, deberás adaptar las llamadas para inyectar el token en el header `Authorization: Bearer ...`.
