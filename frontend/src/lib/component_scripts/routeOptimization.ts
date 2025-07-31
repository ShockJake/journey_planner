import { baseUrl, setCORSHeader } from '$lib/component_scripts/server.ts';
import type OptimizedRoute from '$lib/types/OptimizedRoute.ts';
import axios, { AxiosError } from 'axios';
import mapError from './errorMapper.ts';
import type Place from '$lib/types/Place.ts';

export async function optimizeRoute(
    routeName: string,
    startDate: string,
    startHour: string
): Promise<OptimizedRoute | string> {
    try {
        setCORSHeader()
        const response = await axios.post(`${baseUrl()}/routes/optimize`, {
            routeName: routeName,
            startDateTime: `${startDate}T${startHour}`
        });
        return response.data;
    } catch (error: AxiosError | any) {
        return mapError(error);
    }
}

export async function saveOptimizedRoute(optimizationId: string, routeName: string): Promise<string> {
    try {
        setCORSHeader()
        await axios.post(`${baseUrl()}/routes/save-optimized-route`, {
            optimizationId: optimizationId,
            routeName: routeName
        });
        return '';
    } catch (error: AxiosError | any) {
        return mapError(error);
    }
}

export function isAdditionalPlace(placeName: string, additionalPlaces: Place[]): boolean {
    return additionalPlaces.map((place) => place.name).indexOf(placeName) !== -1;
}
