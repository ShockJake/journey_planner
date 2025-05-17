import axios, { AxiosError } from 'axios';
import { browser } from '$app/environment';
import { writable } from 'svelte/store';
import { goto } from '$app/navigation';
import { baseUrl } from '$lib/component_scripts/server.ts';
import mapError from './errorMapper.ts';

const storedToken = browser ? localStorage.getItem("token") : null;
export const authToken = writable(storedToken);
authToken.subscribe((value) => {
    if (!browser) return;
    if (value) {
        localStorage.setItem("token", value);
    } else {
        localStorage.removeItem("token");
    }
});

export const isAuthenticated = writable(!!storedToken);
authToken.subscribe((token) => isAuthenticated.set(!!token))

export async function register(username: string, password: string): Promise<string> {
    if (!username || username === "" || !password || password === "") {
        return "Blank values are forbiden";
    }

    try {
        await axios.post(`${baseUrl()}/auth/register`, { username, password });
        return "";
    } catch (error: AxiosError | any) {
        return mapError(error);
    }
}

export async function login(username: string, password: string): Promise<string> {
    if (!username || username === "" || !password || password === "") {
        return "Blank values are forbiden";
    }

    try {
        const response = await axios.post(`${baseUrl()}/auth/login`, { username, password });
        const token = response.data.token;
        if (token !== null || token !== undefined) {
            authToken.set(token);
            axios.defaults.headers.common = { "Authorization": `Bearer ${token}` }
            goto("/account");
        }
        return "";
    } catch (error: AxiosError | any) {
        return mapError(error);
    }
}

export function logout() {
    localStorage.removeItem("token");
    window.location.href = "/"
}
