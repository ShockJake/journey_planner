<script lang="ts">
	import { fetchRouteGenerationMetadata } from '$lib/component_scripts/routeGeneration.ts';
	import type ComboboxItem from '$lib/types/ComboboxItem.ts';
	import type RouteGenerationMetadata from '$lib/types/RouteGenerationMetadata.ts';
	import { onMount } from 'svelte';
	import FormBody from './FormBody.svelte';
	import Alert from '$lib/components/common/Alert.svelte';
	import { ShieldAlert, RefreshCw } from '$lib/components/common/Icons.ts';
	import Loader from '$lib/components/common/Loader.svelte';
	import { fade } from 'svelte/transition';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';

	let municipalities: { name: string }[] = $state([]);
	let placeTypes: ComboboxItem[] = $state([]);
	let errorMessage = $state('');
	let loading = $state(false);

	async function fetchData() {
		loading = true;

		const response: RouteGenerationMetadata | string = await fetchRouteGenerationMetadata();
		if (typeof response === 'string') {
			errorMessage = response;
			loading = false;
			return;
		}
		errorMessage = '';
		for (let index = 0; index < response.municipalities.length; index++) {
			const element = response.municipalities[index];
			municipalities.push({ name: element });
		}

		for (let index = 0; index < response.placeTypes.length; index++) {
			const element = response.placeTypes[index];
			placeTypes.push({ id: index + 1, name: element.charAt(0) + element.slice(1).toLowerCase() });
		}
		loading = false;
	}

	onMount(async () => {
		fetchData();
	});
</script>

{#if loading}
	<div in:fade class="flex h-full w-full flex-col items-center justify-center">
		<Loader />
		<span class="text-xs font-medium text-black lg:text-sm">Getting latest route data...</span>
	</div>
{:else if errorMessage.length !== 0}
	<div in:fade class="flex h-full w-full flex-col items-center justify-center gap-2">
		<Alert message={errorMessage} iconSupplier={() => ShieldAlert} type="danger" />
		<button
			type="button"
			onclick={fetchData}
			class="mt-3 rounded-md bg-green-100 px-2 py-2 text-sm font-medium text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-white/75"
			><TextWithIcon text="Try Again" icon={() => RefreshCw} />
		</button>
	</div>
{:else}
	<FormBody {municipalities} {placeTypes} />
{/if}
