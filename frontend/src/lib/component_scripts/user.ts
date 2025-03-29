import axios, { AxiosError } from "axios";
import { writable } from "svelte/store";
import { baseUrl } from "./server.ts";
import { logout } from "./authentication.ts";

export const username = writable("");

export interface UserDataResult {
    username: string,
    routesCreated: number,
    error: string | null;
}

export async function getUserData(): Promise<UserDataResult> {
    try {
        const response = await axios.get(`${baseUrl()}/user`)
        if (response.status !== 200) {
            return { username: "", routesCreated: 0, error: response.data.message }
        }
        const result = response.data;
        return { username: result.username, routesCreated: result.routesCreated, error: null }
    } catch (error: AxiosError | any) {
        return { username: "", routesCreated: 0, error: error.message }
    }
}

export async function changeLogin(newLogin: string): Promise<string> {
    if (newLogin === null || newLogin === "") {
        return "Bad login provided!";
    }
    try {
        const response = await axios.patch(`${baseUrl()}/user`, { infoType: "USERNAME", value: newLogin })
        if (response.status !== 200) {
            return "Bad response";
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
        const response = await axios.patch(`${baseUrl()}/user`, { infoType: "PASSOWRD", value: password })
        if (response.status !== 200)
            return "Cannot change password...";
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
            logout();
            return "Cannot delete account";
        }
        return "";
    } catch (error: AxiosError | any) {
        return "Cannot delete account";
    }
}
