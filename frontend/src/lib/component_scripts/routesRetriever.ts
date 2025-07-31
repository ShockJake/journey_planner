import { baseUrl, setCORSHeader } from '$lib/component_scripts/server.ts';
import type Route from '$lib/types/Route.ts';
import axios, { AxiosError } from 'axios';
import mapErrorFromCode from './errorMapper.ts';

export async function retrievePredefinedRoutes(): Promise<Route[] | string> {
    try {
        setCORSHeader()
        const response = await axios.get(`${baseUrl()}/routes/predefined`, { headers: { "Access-Control-Allow-Origin": window.location.origin } });
        if (response.status !== 200) {
            return mapErrorFromCode(response.status);
        }
        return response.data;
    } catch (error: AxiosError | any) {
        return "No response from server";
    }
}
