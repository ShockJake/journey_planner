import type Route from "$lib/types/Route.ts";

let currentRoute = $state<Route>({
    name: "",
    description: "",
    imageUrl: "",
    center: { lat: 0.0, lng: 0.0 },
    places: []
});

export const currentRouteState = {
    get value() {
        return currentRoute;
    },
    set value(newState) {
        currentRoute = newState;
    }
}
