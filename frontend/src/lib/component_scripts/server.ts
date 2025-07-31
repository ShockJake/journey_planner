import axios from 'axios';
const url = import.meta.env.VITE_BACKEND_BASE_URL ?? 'http://localhost:8080';
//const url = 'http://localhost:8080';

export function baseUrl(): string {
    return url;
}

export function setCORSHeader() {
    axios.defaults.headers.common["Access-Control-Allow-Origin"] = window.location.origin;
}
