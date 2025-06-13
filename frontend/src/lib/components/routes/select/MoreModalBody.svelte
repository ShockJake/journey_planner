<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import {
		List,
		FileText,
		SquareCheck,
		MapPinHouse,
		Save,
		ShieldAlert,
		Check
	} from '$lib/components/common/Icons.ts';
	import type DialogInterface from '$lib/components/common/DialogInterface.ts';
	import Map from '$lib/components/common/Map.svelte';
	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';
	import { isAuthenticated } from '$lib/component_scripts/authentication.ts';
	import { goto } from '$app/navigation';
	import { getIcon } from '$lib/component_scripts/placeIconMapper.ts';
	import { associateRouteWithUser } from '$lib/component_scripts/routeAssociation.ts';
	import LoaderCircle from '$lib/components/common/LoaderCircle.svelte';

	interface Props {
		dialog: DialogInterface;
	}
	const { dialog }: Props = $props();

	let isLoading = $state(false);
	let error = $state('');
	let isSaved = $state(false);
	function gotoRouteOptimization() {
		dialog.close();
		goto('/route/optimize');
	}

	function triggerSaving() {
		if (isSaved) {
			return;
		}
		isLoading = true;
		setTimeout(
			() =>
				associateRouteWithUser(currentRouteState.value).then((output) => {
					isLoading = false;
					error = output;
					if (error === '') {
						isSaved = true;
					}
				}),
			1000
		);
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
		<div class="flex rounded-lg bg-gray-50 p-3 lg:flex-auto" style="width: 500px; height: 500px;">
			<Map showLine={false} />
		</div>
		<div class="flex flex-col pt-1 lg:flex-2/3">
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
			<div class="flex w-full grow items-end justify-end gap-2">
				{#if $isAuthenticated}
					<button
						onclick={triggerSaving}
						class="{error.length > 0
							? 'bg-red-100 text-red-900 hover:bg-red-200 focus-visible:ring-red-500'
							: 'bg-blue-100 text-blue-900 hover:bg-blue-200 focus-visible:ring-blue-500'} flex justify-center rounded-md border border-transparent px-1 py-2 text-sm font-medium text-nowrap transition focus:outline-hidden focus-visible:ring-2 focus-visible:ring-offset-2"
					>
						{#if isLoading}
							<LoaderCircle />
						{:else if error.length > 0}
							<TextWithIcon text={error} icon={() => ShieldAlert} />
						{:else if isSaved}
							<Check />
						{:else}
							<TextWithIcon text="Save for later" icon={() => Save} />
						{/if}
					</button>
				{/if}

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
