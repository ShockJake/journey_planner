<script lang="ts">
	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';
	import OptimizeRoute from '$lib/components/routes/optimize/OptimizeRoute.svelte';
</script>

<svelte:head>
	<title>{currentRouteState.value.name}</title>
</svelte:head>

<div class="relative z-10 m-3 flex h-full flex-col items-center">
	<div class="flex h-9/10 w-9/10 items-center justify-center rounded-lg bg-white/65">
		<OptimizeRoute />
	</div>
</div>
