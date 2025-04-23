<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { List, FileText, SquareCheck, MapPinHouse } from '$lib/components/common/Icons.ts';
	import type DialogInterface from '$lib/components/common/DialogInterface.ts';
	import Map from '$lib/components/common/Map.svelte';
	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';
	import { goto } from '$app/navigation';
	import { getIcon } from '$lib/component_scripts/placeIconMapper.ts';

	export let dialog: DialogInterface;

	function gotoRouteOptimization() {
		dialog.close();
		goto('/route/optimize');
	}
</script>

<div class="w-full transition">
	<div class="flex w-full items-center justify-center">
		<h1 class="text-2xl leading-6 font-bold text-gray-900">
			<span class="flex items-center">{currentRouteState.value.name}</span>
		</h1>
	</div>
	<div class="my-3 border-t border-gray-300"></div>
	<div class="flex flex-col gap-3 lg:flex-row">
		<div class="flex rounded-lg bg-gray-50 p-3 lg:flex-1/3" style="width: 500px; height: 500px;">
			<Map />
		</div>
		<div class="lg:flex-2/3">
			<h3 class="text-md py-1 font-bold text-gray-700">
				<TextWithIcon text={currentRouteState.value.municipality} icon={() => MapPinHouse} />
			</h3>
			<div class="my-3 border-t border-gray-300"></div>
			<h3 class="text-md py-1 font-bold text-gray-700">
				<TextWithIcon text="Description" icon={() => FileText} />
			</h3>
			<div class="pl-4 text-sm font-medium text-gray-500">
				{currentRouteState.value.description}
			</div>
			<div class="my-3 border-t border-gray-300"></div>
			<h3 class="text-md pt-2 pb-1 font-bold text-gray-700">
				<TextWithIcon text="Key Points" icon={() => List} />
			</h3>
			<div class="pl-2 text-sm font-medium text-gray-600">
				<ul class="flex flex-col gap-1">
					{#each currentRouteState.value.places as place}
						<li>
							<TextWithIcon text={place.name} icon={() => getIcon(place.placeType)} />
						</li>
					{/each}
				</ul>
			</div>
			<div class="mt-3 flex w-full items-end justify-end">
				<button
					onclick={gotoRouteOptimization}
					class="flex justify-center rounded-md border border-transparent bg-green-100 py-2 pr-2 pl-1 text-sm font-medium text-nowrap text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-green-500 focus-visible:ring-offset-2"
				>
					<TextWithIcon text="Select" icon={() => SquareCheck} />
				</button>
			</div>
		</div>
	</div>
</div>
