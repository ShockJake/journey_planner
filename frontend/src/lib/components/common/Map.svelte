<script lang="ts">
	import 'leaflet/dist/leaflet.css';
	import { Map, TileLayer, Marker, Popup, Polyline } from 'sveaflet';
	import type { LatLngExpression } from 'leaflet';
	import type Place from '$lib/types/Place.ts';
	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';
	import { currentPathState } from '$lib/component_scripts/currentPath.svelte.ts';
	import type Point from '$lib/types/Point.ts';
	import type Path from '$lib/types/Path.ts';

	interface Props {
		showLine: boolean;
	}

	const { showLine }: Props = $props();

	let mapRef: any = $state();
	let center: LatLngExpression = $state([0.0, 0.0]);
	let places: Place[] = $state([]);
	let initialPath: Path = { time: 0, distance: 0, points: [] };
	let path: Path = $state(initialPath);

	$effect(() => {
		const value = currentRouteState.value;
		if (value.center !== undefined) {
			center = [value.center.lat, value.center.lng];
		} else if (value.centerLatitude !== undefined && value.centerLongitude !== undefined) {
			center = [value.centerLatitude, value.centerLongitude];
		}
		places = currentRouteState.value.places;
		path = currentPathState.value;
	});
	const zoom = 13;

	function getLatLng(place: Place): LatLngExpression {
		if (place.position !== undefined) {
			return [place.position.lat, place.position.lng];
		} else if (place.latitude !== undefined && place.longitude !== undefined) {
			return [place.latitude, place.longitude];
		}
		return [0, 0];
	}

	function getLatLngFromPath(point: Point): LatLngExpression {
		return [point.lat, point.lng];
	}

	function getAllLatLngs(path: Path): LatLngExpression[] {
		return path.points.map((item) => getLatLngFromPath(item));
	}
</script>

<Map bind:instance={mapRef} options={{ center, zoom }}>
	<TileLayer
		url={'https://tile.openstreetmap.org/{z}/{x}/{y}.png'}
		options={{
			attribution: '&copy; OpenStreetMap contributors'
		}}
	/>
	{#each places as place, index}
		<Marker latLng={getLatLng(place)}>
			<Popup>{index + 1}. {place.name}</Popup>
		</Marker>
	{/each}
	{#if showLine}
		<Polyline latLngs={getAllLatLngs(path)} options={{ color: '#3d8ac9', weight: 4 }} />
	{/if}
</Map>
