<script lang="ts">
	import { getIcon } from '$lib/component_scripts/placeIconMapper.ts';
	import {
		Check,
		FileText,
		Info,
		MapPinHouse,
		Route as RouteIcon,
		SquareCheck
	} from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import type Route from '$lib/types/Route.ts';
	import { fade } from 'svelte/transition';
	import { isAuthenticated } from '$lib/component_scripts/authentication.ts';
	import { currentRouteState, isSavedRoute } from '$lib/component_scripts/currentRoute.svelte.ts';
	import { goto } from '$app/navigation';

	interface Props {
		route: Route;
	}

	const { route }: Props = $props();

	function gotoRouteOptimization() {
		currentRouteState.value = route;
		goto('/route/optimize');
	}
</script>

<div in:fade class="m-2 overflow-y-scroll rounded-lg bg-white/30">
	<h2
		class="flex w-full items-center justify-start rounded-lg bg-white/90 py-2 pl-2 text-xl font-bold text-gray-800 drop-shadow-sm"
	>
		<div class="w-auto">
			<TextWithIcon text="Route Info" icon={() => Info} />
		</div>
	</h2>
	<div class="flex flex-col justify-center gap-3 p-3 lg:flex-row">
		<div class="flex w-full items-center justify-center rounded-lg lg:w-auto">
			<img class="h-68 rounded-lg object-cover" src={route.imageUrl} alt="test-img" />
		</div>
		<div class="flex flex-col gap-3 lg:flex-2/3">
			<div class="text-md rounded-lg bg-white px-1 py-2 font-bold">
				<TextWithIcon text={route.name} icon={() => RouteIcon} />
			</div>
			<div class="text-md rounded-lg bg-white px-1 py-2 font-bold">
				<TextWithIcon text={route.municipality} icon={() => MapPinHouse} />
			</div>
			<div class="text-md grow rounded-lg bg-white px-1 py-2 font-medium">
				<TextWithIcon text="Description:" icon={() => FileText} />
				<p class="ml-2 pt-1 text-sm font-medium text-gray-700">{route.description}</p>
			</div>
		</div>
	</div>
	<div class="pl-2 text-sm font-medium text-gray-600">
		<ul class="flex flex-col gap-1">
			{#each route.places as place}
				<li>
					<TextWithIcon text={place.name} icon={() => getIcon(place.placeType)} />
				</li>
			{/each}
		</ul>
	</div>
	<div class="flex w-full flex-row items-end justify-end gap-1 p-2">
		{#if $isAuthenticated && isSavedRoute(route)}
			<button
				onclick={() => {}}
				class="flex justify-center rounded-md border border-transparent bg-blue-100 px-1 py-2 text-sm font-medium text-nowrap text-blue-900 transition hover:bg-blue-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
			>
				<TextWithIcon text="Saved" icon={() => Check} />
			</button>
		{/if}
		<button
			class="flex justify-center rounded-md border border-transparent bg-green-100 py-2 pr-2 pl-1 text-sm font-medium text-nowrap text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-green-500 focus-visible:ring-offset-2"
			onclick={gotoRouteOptimization}
		>
			<TextWithIcon text="Optimize" icon={() => SquareCheck} />
		</button>
	</div>
</div>
