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

let savedRoutes = $state<string[]>([]);

export const savedRoutesState = {
    get value() {
        return savedRoutes;
    },
    set value(newState) {
        savedRoutes = newState;
    }
}

export function updateSavedRoutes(route: Route): void {
    const routeName = route.name;
    if (savedRoutesState.value.indexOf(routeName) === -1) {
        savedRoutesState.value.push(routeName);
    }
}

export function isSavedRoute(route: Route): boolean {
    return savedRoutesState.value.indexOf(route.name) !== -1;
}
