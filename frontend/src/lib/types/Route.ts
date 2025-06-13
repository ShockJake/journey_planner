import type Place from './Place.ts';

export default interface Route {
	name: string;
	municipality: string;
	description: string;
	imageUrl: string;
	center: {
		lat: number;
		lng: number;
	};
	places: Place[];
	additionalPlaces: Place[];
}
