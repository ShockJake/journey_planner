import { baseUrl } from '$lib/component_scripts/server.ts';
import type OptimizedRoute from '$lib/types/OptimizedRoute.ts';
import axios, { AxiosError } from 'axios';

export async function optimizeRoute(routeName: string, startDate: string, startHour: string): Promise<OptimizedRoute | null> {
    try {
        const response = await axios.post(`${baseUrl()}/routes/optimize`,
            { routeName: routeName, startDateTime: `${startDate}T${startHour}` });
        if (response.status !== 200) {
            return null;
        }
        return response.data;
    } catch (error: AxiosError | any) {
        return null;
    }
}
