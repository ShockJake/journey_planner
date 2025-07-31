import axios, { AxiosError } from 'axios';
import mapError from './errorMapper.ts';
import { baseUrl, setCORSHeader } from './server.ts';
import type Route from "$lib/types/Route.ts";

export async function associateRouteWithUser(route: Route): Promise<string> {
    try {
        setCORSHeader()
        await axios.post(`${baseUrl()}/routes/associate-with-user`, { routeName: route.name })
        return '';
    } catch (error: AxiosError | any) {
        return mapError(error);
    }
}
