const url = import.meta.env.VITE_BACKEND_BASE_URL ?? 'http://localhost:8080';
//const url = 'http://localhost:8080';

export function baseUrl(): string {
    return url;
}
