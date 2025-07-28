import axios, { AxiosError } from 'axios';
import mapError from './errorMapper.ts';
import { baseUrl } from './server.ts';
import type Route from "$lib/types/Route.ts";

export async function associateRouteWithUser(route: Route): Promise<string> {
    try {
        await axios.post(`${baseUrl()}/routes/associate-with-user`, { routeName: route.name })
        return '';
    } catch (error: AxiosError | any) {
        return mapError(error);
    }
}
