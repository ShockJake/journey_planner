import type Route from '$lib/types/Route.ts';

let initialRoute: Route = {
    name: '',
    description: '',
    imageUrl: '',
    center: { lat: 0.0, lng: 0.0 },
    places: [],
    municipality: '',
    additionalPlaces: []
};

let currentRoute = $state<Route>(initialRoute);

export const currentRouteState = {
    get value() {
        return currentRoute;
    },
    set value(newState) {
        currentRoute = newState;
    }
};

export function resetCurrentRouteState() {
    currentRouteState.value = initialRoute;
}
