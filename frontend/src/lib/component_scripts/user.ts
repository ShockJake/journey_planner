import axios, { AxiosError } from "axios";
import { writable } from "svelte/store";
import { baseUrl, setCORSHeader } from "./server.ts";
import { logout } from "./authentication.ts";
import type UserDataResult from "$lib/types/UserDataResult.ts";
import mapErrorFromCode from "./errorMapper.ts";

export const username = writable("");

export async function getUserData(): Promise<UserDataResult> {
    try {
        const token = localStorage.getItem('token');

        setCORSHeader()
        const response = await axios.get(`${baseUrl()}/user`, { headers: { "Authorization": `Bearer ${token}` } });
        if (response.status !== 200) {
            return { username: "", createdAt: '', error: response.data.message, routes: [], optimizedRoutes: [] }
        }
        const result = response.data;
        return {
            username: result.username,
            createdAt: result.createdAt,
            error: null,
            routes: result.routes,
            optimizedRoutes: result.optimizedRoutes
        }
    } catch (error: AxiosError | any) {
        return { username: "", createdAt: '', error: error.message, routes: [], optimizedRoutes: [] }
    }
}

export async function changeLogin(newLogin: string): Promise<string> {
    if (newLogin === null || newLogin === "") {
        return "Bad login provided!";
    }
    try {
        setCORSHeader()
        const response = await axios.patch(`${baseUrl()}/user`, { infoType: "USERNAME", value: newLogin })
        if (response.status !== 200) {
            return mapErrorFromCode(response.status);
        }
        logout();
        return "";
    } catch (error: AxiosError | any) {
        return "Cannot change login";
    }
}

export async function changePassword(password: string): Promise<string> {
    if (password === null || password === "") {
        return "Bad password provided!";
    }
    try {
        setCORSHeader()
        const response = await axios.patch(`${baseUrl()}/user`, { infoType: "PASSOWRD", value: password })
        if (response.status !== 200)
            return mapErrorFromCode(response.status);
        logout();
        return "";
    } catch (error: AxiosError | any) {
        return "Cannot change password...";
    }
}

export async function deleteAccount(): Promise<string> {
    try {
        const response = await axios.delete(`${baseUrl()}/user`);
        if (response.status !== 200) {
            return mapErrorFromCode(response.status);
        }
        logout();
        return "";
    } catch (error: AxiosError | any) {
        return "Cannot delete account";
    }
}
