<script lang="ts">
	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';
	import OptimizeRoute from '$lib/components/routes/optimize/OptimizeRoute.svelte';
	import { fade } from 'svelte/transition';
</script>

<svelte:head>
	<title>{currentRouteState.value.name}</title>
</svelte:head>

<div in:fade class="relative z-10 m-3 flex h-full flex-col items-center overflow-y-scroll">
	<div
		class="flex h-auto w-99/100 items-center justify-center rounded-lg bg-white/65 lg:h-full lg:w-9/10"
	>
		<OptimizeRoute />
	</div>
</div>
