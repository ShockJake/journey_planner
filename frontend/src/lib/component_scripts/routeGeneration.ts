import axios, { AxiosError } from 'axios';
import mapError from './errorMapper.ts';
import { baseUrl, setCORSHeader } from './server.ts';
import type RouteGenerationMetadata from '$lib/types/RouteGenerationMetadata.ts';
import type RouteGenerationRequest from '$lib/types/RouteGenerationRequest.ts';
import type Route from '$lib/types/Route.ts';

export async function fetchRouteGenerationMetadata(): Promise<RouteGenerationMetadata | string> {
    try {
        setCORSHeader()
        const response = await axios.get(`${baseUrl()}/routes/generate/metadata`);
        return response.data;
    } catch (error: AxiosError | any) {
        return mapError(error);
    }
}

export async function generateRoute(request: RouteGenerationRequest): Promise<Route | string> {
    try {
        request.creationDateTime = new Date().toISOString();
        setCORSHeader()
        const response = await axios.post(`${baseUrl()}/routes/generate`, request);
        return response.data.route;
    } catch (error: AxiosError | any) {
        return mapError(error);
    }
}
