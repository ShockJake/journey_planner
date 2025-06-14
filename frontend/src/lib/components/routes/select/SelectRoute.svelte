<script lang="ts">
	import { onMount } from 'svelte';
	import Alert from '$lib/components/common/Alert.svelte';
	import Header from './Header.svelte';
	import { CircleAlert } from '$lib/components/common/Icons.ts';
	import RouteSection from './RouteSection.svelte';
	import type DialogInterface from '$lib/components/common/DialogInterface.ts';
	import ModalRenderSized from '$lib/components/common/ModalRenderSized.svelte';
	import { createDialog } from 'svelte-headlessui';
	import MoreModalBody from './MoreModalBody.svelte';
	import type Route from '$lib/types/Route.ts';
	import { retrievePredefinedRoutes } from '$lib/component_scripts/routesRetriever.ts';
	import Loader from '$lib/components/common/Loader.svelte';
	import { fade } from 'svelte/transition';
	import { resetCurrentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';

	export let dialog: DialogInterface = createDialog({ label: 'moreInfoModal' });
	let routes: Route[] = [];
	let loading = false;
	let error: string = '';

	onMount(() => {
		loading = true;
		retrievePredefinedRoutes().then((result) => {
			if (typeof result === 'string') {
				error = result;
			} else {
				routes = result;
				resetCurrentRouteState();
			}
			loading = false;
		});
	});
</script>

<div class="flex h-full w-full flex-col">
	<Header />
	<div class="flex min-h-0 w-full grow flex-row">
		{#if loading}
			<div in:fade class="flex grow items-center justify-center">
				<Loader />
			</div>
		{:else if error.length === 0}
			{#if routes.length !== 0}
				<div
					in:fade
					class="grid w-full grid-cols-1 gap-2 overflow-scroll p-3 md:grid-cols-2 lg:grid-cols-3"
				>
					{#each routes as route}
						<RouteSection showDialogAction={dialog.open} {route} />
					{/each}
				</div>
			{:else}
				<div in:fade class="flex h-full w-full flex-col items-center justify-center">
					<h1 class="mb-6 text-3xl font-bold text-gray-800">Nothing to show</h1>
				</div>
			{/if}
		{:else}
			<div in:fade class="flex grow items-center justify-center">
				<Alert message={error} type="danger" iconSupplier={() => CircleAlert} />
			</div>
		{/if}
	</div>
</div>

<ModalRenderSized body={() => MoreModalBody} {dialog} height="h-full" width="w-8/10" />
