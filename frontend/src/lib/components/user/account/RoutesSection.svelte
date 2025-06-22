<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { Route as RouteIcon } from '$lib/components/common/Icons.ts';
	import type Route from '$lib/types/Route.ts';
	import { fade } from 'svelte/transition';
	import RouteSection from './route/RouteSection.svelte';

	interface Props {
		routes: Route[];
	}
	const { routes }: Props = $props();
</script>

<div class="{routes.length > 0 ? '' : 'min-h-0 grow '} rounded-lg bg-white/70">
	<h2
		class="flex w-full items-center justify-center rounded-lg bg-white/80 py-2 text-2xl font-bold text-gray-800 drop-shadow-md"
	>
		<div class="w-auto">
			<TextWithIcon text="Saved Routes" icon={() => RouteIcon} />
		</div>
	</h2>
	{#if routes.length === 0}
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
			{#each routes as route}
				<RouteSection {route} />
			{/each}
		</div>
	{/if}
</div>
