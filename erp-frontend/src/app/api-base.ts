// Runtime API base helper. Uses window.__BACKEND_URL__ injected by config.js when available.
export const API_BASE: string = ((): string => {
  const win = (window as any) as any;
  if (win && win.__BACKEND_URL__) {
    const url: string = String(win.__BACKEND_URL__).replace(/\/$/, '');
    return url + '/api';
  }
  return 'http://localhost:8082/api';
})();
