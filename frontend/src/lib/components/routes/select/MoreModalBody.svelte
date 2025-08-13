<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import {
		List,
		FileText,
		SquareCheck,
		MapPinHouse,
		Save,
		ShieldAlert,
		Check,
		CircleX
	} from '$lib/components/common/Icons.ts';
	import type DialogInterface from '$lib/components/common/DialogInterface.ts';
	import Map from '$lib/components/common/Map.svelte';
	import {
		currentRouteState,
		isSavedRoute,
		updateSavedRoutes
	} from '$lib/component_scripts/currentRoute.svelte.ts';
	import { isAuthenticated } from '$lib/component_scripts/authentication.ts';
	import { goto } from '$app/navigation';
	import { getIcon } from '$lib/component_scripts/placeIconMapper.ts';
	import { associateRouteWithUser } from '$lib/component_scripts/routeAssociation.ts';
	import LoaderCircle from '$lib/components/common/LoaderCircle.svelte';
	import { fade } from 'svelte/transition';
	import Button from '$lib/components/common/Button.svelte';

	interface Props {
		dialog: DialogInterface;
	}
	const { dialog }: Props = $props();

	let isLoading = $state(false);
	let error = $state('');

	function gotoRouteOptimization() {
		dialog.close();
		goto('/route/optimize');
	}

	function triggerSaving() {
		if (isSavedRoute(currentRouteState.value)) {
			return;
		}
		isLoading = true;
		associateRouteWithUser(currentRouteState.value).then((output) => {
			isLoading = false;
			error = output;
			if (error === '') {
				updateSavedRoutes(currentRouteState.value);
			}
		});
	}
</script>

<div class="w-full transition">
	<div class="flex w-full items-center justify-center">
		<h1 class="text-sm leading-6 font-bold text-gray-900 lg:text-2xl">
			<span class="flex items-center">{currentRouteState.value.name}</span>
		</h1>
	</div>
	<div class="my-3 border-t border-gray-300"></div>
	<div class="flex flex-col gap-3 lg:flex-row">
		<style>
			#map-container {
				width: 330px;
				height: 330px;
			}
			@media (width >= 64rem) {
				#map-container {
					width: 500px;
					height: 500px;
				}
			}
		</style>
		<div id="map-container" class="flex rounded-lg bg-gray-50 p-3 lg:flex-auto">
			<Map showLine={false} />
		</div>
		<div class="flex flex-col pt-1 lg:flex-2/3">
			<h3 class="py-1 text-sm font-bold text-gray-700 lg:text-base">
				<TextWithIcon text={currentRouteState.value.municipality} icon={() => MapPinHouse} />
			</h3>
			<div class="my-3 border-t border-gray-300"></div>
			<h3 class="py-1 text-sm font-bold text-gray-700 lg:text-base">
				<TextWithIcon text="Description" icon={() => FileText} />
			</h3>
			<div class="pl-4 text-xs font-medium text-gray-500 lg:text-sm">
				{currentRouteState.value.description}
			</div>
			<div class="my-3 border-t border-gray-300"></div>
			<h3 class="pt-2 pb-1 text-sm font-bold text-gray-700 lg:text-base">
				<TextWithIcon text="Key Points" icon={() => List} />
			</h3>
			<div class="pl-2 text-xs font-medium text-gray-600 lg:text-sm">
				<ul class="flex flex-col gap-1">
					{#each currentRouteState.value.places as place}
						<li>
							<TextWithIcon text={place.name} icon={() => getIcon(place.placeType)} />
						</li>
					{/each}
				</ul>
			</div>
			<div class="flex w-full grow items-end justify-end gap-2 pt-2">
				{#if $isAuthenticated}
					<button
						onclick={triggerSaving}
						class="{error.length > 0
							? 'bg-red-100 text-red-900 hover:bg-red-200 focus-visible:ring-red-500'
							: 'bg-blue-100 text-blue-900 hover:bg-blue-200 focus-visible:ring-blue-500'} flex justify-center rounded-md border border-transparent px-1 py-2 text-xs font-medium text-nowrap transition focus:outline-hidden focus-visible:ring-2 focus-visible:ring-offset-2 lg:text-sm"
					>
						{#if isLoading}
							<div in:fade>
								<LoaderCircle />
							</div>
						{:else if error.length > 0}
							<div in:fade>
								<TextWithIcon text={error} icon={() => ShieldAlert} />
							</div>
						{:else if isSavedRoute(currentRouteState.value)}
							<div in:fade>
								<TextWithIcon text="Saved" icon={() => Check} />
							</div>
						{:else}
							<div in:fade>
								<TextWithIcon text="Save for later" icon={() => Save} />
							</div>
						{/if}
					</button>
				{/if}

				<Button
					text="Select"
					action={gotoRouteOptimization}
					iconProvider={() => SquareCheck}
					color="green"
				/>
				<Button text="Close" iconProvider={() => CircleX} action={dialog.close} color="amber" />
			</div>
		</div>
	</div>
</div>
