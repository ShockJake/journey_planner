import type Place from "$lib/types/Place.ts";
import type Route from "$lib/types/Route.ts";

function formatCoordinates(place: Place): string {
    const position = place.position;
    return `${position.lat},${position.lng}`;
}

export default function goToGoogleMaps(route: Route) {
    const places = route.places;
    const origin = formatCoordinates(places.at(0)!);
    const destination = formatCoordinates(places.at(places.length - 1)!);
    const waypoints = places.slice(1, places.length - 2)
        .map(place => formatCoordinates(place))
        .join("|");
    const url = `https://google.com/maps/dir/?api=1&origin=${origin}&destination=${destination}&waypoints=${waypoints}&travelmode=walking`
    window.open(url, '_blank');
}
