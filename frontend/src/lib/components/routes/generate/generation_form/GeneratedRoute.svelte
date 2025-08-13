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
	import SectionHeader from '$lib/components/common/SectionHeader.svelte';
	import Button from '$lib/components/common/buttons/Button.svelte';

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
	<SectionHeader text="Route Info" icon={() => Info} />
	<div class="flex flex-col justify-center gap-3 p-3 lg:flex-row">
		<div class="flex w-full items-center justify-center rounded-lg lg:w-auto">
			<img class="h-68 rounded-lg object-cover" src={route.imageUrl} alt="test-img" />
		</div>
		<div class="flex flex-col gap-3 lg:flex-2/3">
			<div class="rounded-lg bg-white px-1 py-2 text-sm font-bold lg:text-base">
				<TextWithIcon text={route.name} icon={() => RouteIcon} />
			</div>
			<div class="rounded-lg bg-white px-1 py-2 text-sm font-bold lg:text-base">
				<TextWithIcon text={route.municipality} icon={() => MapPinHouse} />
			</div>
			<div class="grow rounded-lg bg-white px-1 py-2 text-sm font-medium lg:text-base">
				<TextWithIcon text="Description:" icon={() => FileText} />
				<p class="ml-2 pt-1 text-xs font-medium text-gray-700 lg:text-sm">{route.description}</p>
			</div>
		</div>
	</div>
	<div class="pl-2 text-sm font-medium text-gray-600">
		<ul class="flex flex-col gap-1 text-sm lg:text-base">
			{#each route.places as place}
				<li>
					<TextWithIcon text={place.name} icon={() => getIcon(place.placeType)} />
				</li>
			{/each}
		</ul>
	</div>
	<div class="flex w-full flex-row items-end justify-end gap-1 p-2">
		{#if $isAuthenticated && isSavedRoute(route)}
			<Button action={() => {}} text="Saved" iconProvider={() => Check} color="blue" />
		{/if}
		<Button
			action={gotoRouteOptimization}
			text="Optimize"
			color="green"
			iconProvider={() => SquareCheck}
		/>
	</div>
</div>
