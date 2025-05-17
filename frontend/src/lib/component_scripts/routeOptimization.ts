import { baseUrl } from '$lib/component_scripts/server.ts';
import type OptimizedRoute from '$lib/types/OptimizedRoute.ts';
import axios, { AxiosError } from 'axios';
import mapError from './errorMapper.ts';

export async function optimizeRoute(routeName: string, startDate: string, startHour: string): Promise<OptimizedRoute | string> {
    try {
        const response = await axios.post(`${baseUrl()}/routes/optimize`,
            { routeName: routeName, startDateTime: `${startDate}T${startHour}` });
        return response.data;
    } catch (error: AxiosError | any) {
        return mapError(error);
    }
}
