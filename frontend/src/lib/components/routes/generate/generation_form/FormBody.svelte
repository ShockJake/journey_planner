<script lang="ts">
	import { isAuthenticated } from '$lib/component_scripts/authentication.ts';
	import {
		MapPinHouse,
		LandPlot,
		Waypoints,
		Save,
		Dot,
		Ellipsis,
		GripHorizontal,
		CircleAlert
	} from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import type ComboboxItem from '$lib/types/ComboboxItem.ts';
	import Combobox from '$lib/components/common/Form/Combobox.svelte';
	import ComboboxMulti from '$lib/components/common/Form/ComboboxMulti.svelte';
	import RadioGroup from '$lib/components/common/Form/RadioGroup.svelte';
	import Toggle from '$lib/components/common/Form/Toggle.svelte';
	import type RadioGroupItem from '$lib/types/RadioGroupItem.ts';
	import { generateRoute } from '$lib/component_scripts/routeGeneration.ts';
	import type RouteGenerationRequest from '$lib/types/RouteGenerationRequest.ts';
	import { fade } from 'svelte/transition';
	import Loader from '$lib/components/common/Loader.svelte';
	import type Route from '$lib/types/Route.ts';
	import GeneratedRoute from './GeneratedRoute.svelte';
	import { updateSavedRoutes } from '$lib/component_scripts/currentRoute.svelte.ts';

	interface Props {
		municipalities: { name: string }[];
		placeTypes: ComboboxItem[];
	}

	const { municipalities, placeTypes }: Props = $props();
	let saveToAccount: boolean = $state(false);
	let route: Route | undefined = $state();
	let error = $state('');
	let loading = $state(false);
	let chosenMunicipality: string = '';
	let chosenPlaceTypes: ComboboxItem[] = [];
	let chosenRouteLongevity = '';
	const radioGroupItems: RadioGroupItem[] = [
		{ value: 'Short', description: 'Up to 4 places', iconProvider: () => Dot },
		{ value: 'Normal', description: 'Up to 8 places', iconProvider: () => Ellipsis },
		{ value: 'Long', description: 'Up to 16 places', iconProvider: () => GripHorizontal }
	];

	function toggleSaveToAccount() {
		saveToAccount = !saveToAccount;
	}

	function changeChosenRouteLongevity(longevity: string) {
		chosenRouteLongevity = longevity;
	}

	function changeChosenMunicipality(municipality: string) {
		chosenMunicipality = municipality;
	}

	function changeChosenPlaceTypes(placeTypes: ComboboxItem[]) {
		chosenPlaceTypes = placeTypes;
	}

	function reset() {
		route = undefined;
		loading = false;
		error = '';
	}

	function triggerGeneration() {
		route = undefined;
		loading = true;
		const request: RouteGenerationRequest = {
			municipality: chosenMunicipality,
			saveToAccount: saveToAccount,
			places: chosenPlaceTypes.map((item) => item.name.toUpperCase()),
			routeLongevity: chosenRouteLongevity.toUpperCase(),
			creationDateTime: ''
		};

		generateRoute(request)
			.then((result) => {
				if (typeof result === 'string') {
					error = result;
					return;
				}
				route = result;
				if (saveToAccount === true) {
					updateSavedRoutes(route);
				}
			})
			.finally(() => (loading = false));
	}
</script>

<div
	class="flex w-full grow flex-col items-center justify-center rounded-lg bg-white/40 text-black"
>
	{#if loading}
		<div in:fade class="flex grow items-center justify-center">
			<Loader />
		</div>
	{:else if error.length !== 0}
		<div in:fade class="flex h-full w-full flex-col items-center justify-center">
			<div class="my-4 rounded-md bg-red-600 py-2 pr-2 pl-1 font-medium text-white">
				<TextWithIcon text={error} icon={() => CircleAlert} />
			</div>
			<div class="m-2 flex">
				<button
					onclick={reset}
					class="inline-flex w-full justify-center rounded-md border border-transparent bg-green-100 px-4 py-2 text-sm font-medium text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-green-500 focus-visible:ring-offset-2"
					>Try again</button
				>
			</div>
		</div>
	{:else if route !== undefined}
		<GeneratedRoute {route} />
	{:else}
		<div in:fade class="flex w-full grow flex-col items-center gap-2 p-2">
			<div class="flex w-full flex-col items-center gap-2 rounded-lg bg-white/80 p-2 lg:flex-row">
				<div class="flex w-full flex-1/2 flex-row items-center gap-1 p-2 text-xs lg:p-0 lg:text-sm">
					<TextWithIcon text="Place" icon={() => MapPinHouse} />
					<Combobox data={municipalities} callback={changeChosenMunicipality} />
				</div>
				{#if $isAuthenticated}
					<div
						class="flex w-full flex-row items-center gap-1 rounded-lg p-2 transition lg:max-w-fit {saveToAccount
							? ''
							: 'bg-gray-100/60'}"
					>
						<div
							class="{saveToAccount
								? 'text-black'
								: 'text-gray-600'} w-full text-xs lg:max-w-fit lg:text-sm"
						>
							<TextWithIcon text="Save to account" icon={() => Save} />
						</div>
						<Toggle label="SaveToAccount" toggleFunction={toggleSaveToAccount} />
					</div>
				{/if}
			</div>
			<div class="flex w-full flex-col gap-1 rounded-lg bg-white/80 p-2">
				<div class="text-sm lg:text-lg">
					<TextWithIcon text="Route Longevity" icon={() => Waypoints} />
				</div>
				<div class="text-xs lg:text-sm">
					<RadioGroup items={radioGroupItems} callback={changeChosenRouteLongevity} />
				</div>
			</div>
			<div class="flex w-full flex-col gap-1 rounded-lg bg-white/80 p-2">
				<div class="text-sm lg:text-lg">
					<TextWithIcon text="Attraction Selection" icon={() => LandPlot} />
				</div>
				<div class="text-xs lg:text-sm">
					<ComboboxMulti data={placeTypes} callback={changeChosenPlaceTypes} />
				</div>
			</div>
		</div>
		<div class="flex w-full">
			<div class="m-2 flex w-full">
				<button
					onclick={triggerGeneration}
					class="inline-flex w-full justify-center rounded-md border border-transparent bg-green-100 px-4 py-2 text-xs font-medium text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-green-500 focus-visible:ring-offset-2 lg:text-sm"
					>Generate</button
				>
			</div>
		</div>
	{/if}
</div>
