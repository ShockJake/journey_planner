import type Place from './Place.ts';

export default interface Route {
    name: string;
    municipality: string;
    description: string;
    imageUrl: string;
    center: {
        lat: number;
        lng: number;
    } | undefined;
    centerLatitude: number | undefined;
    centerLongitude: number | undefined;
    places: Place[];
    additionalPlaces: Place[];
}
