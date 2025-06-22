<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { Route as RouteIcon } from '$lib/components/common/Icons.ts';
	import { fade } from 'svelte/transition';
	import type OptimizedRoute from '$lib/types/OptimizedRoute.ts';
	import OptimizedRouteSection from './route/OptimizedRouteSection.svelte';

	interface Props {
		optimizedRoutes: OptimizedRoute[];
	}
	const { optimizedRoutes }: Props = $props();
</script>

<div class="{optimizedRoutes.length > 0 ? '' : 'min-h-0 grow '} rounded-lg bg-white/70">
	<h2
		class="flex w-full items-center justify-center rounded-lg bg-white/80 py-2 text-2xl font-bold text-gray-800 drop-shadow-md"
	>
		<div class="w-auto">
			<TextWithIcon text="Optimized Routes" icon={() => RouteIcon} />
		</div>
	</h2>
	{#if optimizedRoutes.length === 0}
		<div
			class="flex h-9/10 w-full items-center justify-center p-3 text-center text-xl text-gray-800"
		>
			Nothing to show.
		</div>
	{:else}
		<div
			in:fade
			class="grid w-full grid-cols-1 gap-2 overflow-scroll p-3 md:grid-cols-2 lg:grid-cols-3"
		>
			{#each optimizedRoutes as optimizedRoute}
				<OptimizedRouteSection route={optimizedRoute.route} />
			{/each}
		</div>
	{/if}
</div>
