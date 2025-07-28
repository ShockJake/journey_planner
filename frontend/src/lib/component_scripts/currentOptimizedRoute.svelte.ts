import type Route from '$lib/types/Route.ts';
import type OptimizedRoute from '$lib/types/OptimizedRoute.ts';

let initialRoute: Route = {
    name: '',
    description: '',
    imageUrl: '',
    center: { lat: 0.0, lng: 0.0 },
    places: [],
    municipality: '',
    additionalPlaces: []
};

let initialOptimizedRoute: OptimizedRoute = {
    optimizationId: '',
    route: initialRoute,
    weatherInfo: {
        generalData: {
            maxTemperature: 1,
            maxWindSpeed: 1,
            meanTemperature: 1,
            minTemperature: 1
        },
        windData: {
            averageSpeed: 0,
            intensity: '',
            description: ''
        },
        rainData: [
            {
                startHour: 0,
                endHour: 0,
                maxHour: 0,
                maxAmount: 0,
                rainIntensity: '',
                rainDescription: ''
            }
        ]
    },
    path: {
        time: 0,
        distance: 0,
        points: [{
            lat: 0,
            lng: 0
        }]
    }
};

let currentOptimizedRoute = $state<OptimizedRoute>(initialOptimizedRoute);

export const currentOptimizedRouteState = {
    get value() {
        return currentOptimizedRoute;
    },
    set value(newState) {
        currentOptimizedRoute = newState;
    }
};

export function resetCurrentRouteState() {
    currentOptimizedRouteState.value = initialOptimizedRoute;
}

let savedOptimizedRoutes = $state<string[]>([]);

export const savedOptimizedRoutesState = {
    get value() {
        return savedOptimizedRoutes;
    },
    set value(newState) {
        savedOptimizedRoutes = newState;
    }
};

export function updateSavedOptimizedRoutes(optimizationId: string) {
    if (savedOptimizedRoutesState.value.indexOf(optimizationId) === -1) {
        savedOptimizedRoutesState.value.push(optimizationId);
    }
}

export function isSavedOptimizedRoute(optimizationId: string): boolean {
    console.log("Saved optimizations:", $state.snapshot(savedOptimizedRoutesState.value))
    return savedOptimizedRoutesState.value.indexOf(optimizationId) !== -1
}
