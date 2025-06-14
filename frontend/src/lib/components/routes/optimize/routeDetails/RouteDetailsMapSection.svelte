<script lang="ts">
	import Map from '$lib/components/common/Map.svelte';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import {
		LandPlot,
		List,
		Footprints,
		Timer,
		MapPinPlusInside,
		Save
	} from '$lib/components/common/Icons.ts';
	import type Route from '$lib/types/Route.ts';
	import { getIcon } from '$lib/component_scripts/placeIconMapper.ts';
	import { isAdditionalPlace } from '$lib/component_scripts/routeOptimization.ts';
	import { isAuthenticated } from '$lib/component_scripts/authentication.ts';
	import type Path from '$lib/types/Path.ts';
	import { fade } from 'svelte/transition';

	interface Props {
		route: Route;
		path: Path;
	}
	const { route, path }: Props = $props();

	console.log(route);

	function getTime(seconds: number) {
		return seconds / 60;
	}
</script>

<div in:fade class="flex w-full flex-col gap-3 rounded-lg bg-white/90 p-3">
	<div class="flex w-full flex-row items-center">
		<h3 class="flex-1/3 text-2xl font-bold">
			<TextWithIcon text="Plan" icon={() => LandPlot} />
		</h3>
		<div class="flex w-full flex-2/3 items-end justify-end gap-2">
			<button
				type="button"
				class="rounded-md bg-blue-100 py-2 pr-2 pl-1 text-sm font-medium text-blue-900 transition hover:bg-blue-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-white/75"
				onclick={() => {}}
				><TextWithIcon text="Google Maps" icon={() => MapPinPlusInside} />
			</button>

			{#if $isAuthenticated}
				<button
					type="button"
					class="rounded-md bg-green-100 py-2 pr-2 pl-1 text-sm font-medium text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-white/75"
					onclick={() => {}}
					><TextWithIcon text="Save to Account" icon={() => Save} />
				</button>
			{/if}
		</div>
	</div>
	<div class="flex flex-col lg:flex-row">
		<div
			style="height: 500px; width: 500px"
			class="flex-none rounded-lg bg-gray-50 p-2 lg:flex-1/3"
		>
			<Map showLine={true} />
		</div>
		<div class="flex flex-col px-2 lg:flex-2/3">
			<div class="flex w-full flex-col">
				<h3 class="text-md pt-2 pb-1 font-bold">
					<TextWithIcon text="Key Points:" icon={() => List} />
				</h3>
				<div class="pt-1 pl-1 text-sm font-medium text-gray-600">
					<ul class="flex flex-col gap-1">
						{#each route.places as place}
							{#if !isAdditionalPlace(place.name, route.additionalPlaces)}
								<li>
									<TextWithIcon text={place.name} icon={() => getIcon(place.placeType)} />
								</li>
							{:else}
								<li class="text-blue-400">
									<TextWithIcon
										text={`${place.name} - added due to weather conditions`}
										icon={() => getIcon(place.placeType)}
									/>
								</li>
							{/if}
						{/each}
					</ul>
				</div>
			</div>
			<div class="flex min-h-0 w-full grow items-end justify-end">
				<div class="flex flex-row gap-1 rounded-lg bg-green-200 py-2 pr-2 pl-1">
					<TextWithIcon text="{getTime(path.time)} minutes" icon={() => Timer} />
					<div class="border-black-300 h-auto border-l"></div>
					<TextWithIcon text="{path.distance} meters" icon={() => Footprints} />
				</div>
			</div>
		</div>
	</div>
</div>
