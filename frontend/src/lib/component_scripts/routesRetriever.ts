import { baseUrl } from '$lib/component_scripts/server.ts';
import type Route from '$lib/types/Route.ts';
import axios, { AxiosError } from 'axios';

export async function retrievePredefinedRoutes(): Promise<Route[]> {
    try {
        const response = await axios.get(`${baseUrl()}/routes/predefined`);
        if (response.status !== 200) {
            return [];
        }
        return response.data;
    } catch (error: AxiosError | any) {
        return [];
    }
}
