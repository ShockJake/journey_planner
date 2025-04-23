<script lang="ts">
	import 'leaflet/dist/leaflet.css';
	import { Map, TileLayer, Marker, Popup } from 'sveaflet';
	import type { LatLngExpression } from 'leaflet';
	import type Place from '$lib/types/Place.ts';
	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';

	let mapRef: any = $state();
	let center: LatLngExpression = $state([0.0, 0, 0]);
	let places: Place[] = $state([]);

	$effect(() => {
		center = [currentRouteState.value.center.lat, currentRouteState.value.center.lng];
		places = currentRouteState.value.places;
	});
	const zoom = 13;

	function getLatLng(place: Place): LatLngExpression {
		return [place.position.lat, place.position.lng];
	}
</script>

<Map bind:instance={mapRef} options={{ center, zoom }} style="height: 500px; width: 500px">
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
</Map>
