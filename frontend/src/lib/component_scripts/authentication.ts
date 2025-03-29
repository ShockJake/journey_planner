import axios, { AxiosError } from 'axios';
import { browser } from '$app/environment';
import { writable } from 'svelte/store';
import { goto } from '$app/navigation';
import { baseUrl } from '$lib/component_scripts/server.ts';

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

export async function register(username: string, password: string) {
    if (!username || username === "" || !password || password === "") {
        return "Blank values are forbiden";
    }

    try {
        const response = await axios.post(`${baseUrl()}/auth/register`, { username, password });
        if (response.status === 200) {
            return "";
        }
    } catch (error: AxiosError | any) {
        if (error.status === 404) {
            return "Cannot register, try again...";
        }
        return error.message;
    } return "";
}

export async function login(username: string, password: string): Promise<string> {
    if (!username || username === "" || !password || password === "") {
        return "Blank values are forbiden";
    }

    try {
        const response = await axios.post(`${baseUrl()}/auth/login`, { username, password });
        if (response.status == 200) {
            const token = response.data.token;
            if (token !== null || token !== undefined) {
                authToken.set(token);
                axios.defaults.headers.common = { "Authorization": `Bearer ${token}` }
                goto("/account");
            }
        }
    } catch (error: AxiosError | any) {
        console.log(error);
        if (error.status === 404) {
            return "Cannot login, try again...";
        }
        return error.message;
    }
    return "";
}

export function logout() {
    localStorage.removeItem("token");
    window.location.href = "/"
}
