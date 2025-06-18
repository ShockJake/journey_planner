import axios, { AxiosError } from 'axios';
import mapError from './errorMapper.ts';
import { baseUrl } from './server.ts';
import type RouteGenerationMetadata from '$lib/types/RouteGenerationMetadata.ts';

export async function fetchRouteGenerationMetadata(): Promise<RouteGenerationMetadata | string> {
    try {
        const response = await axios.get(`${baseUrl()}/routes/generate/metadata`);
        return response.data;
    } catch (error: AxiosError | any) {
        return mapError(error);
    }
}
