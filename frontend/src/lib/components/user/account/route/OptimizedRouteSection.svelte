<script lang="ts">
	import { CircleEllipsis, Route as RouteIcon } from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { fade } from 'svelte/transition';
	import { goto } from '$app/navigation';
	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';
	import type OptimizedRoute from '$lib/types/OptimizedRoute.ts';
	import { currentOptimizedRouteState } from '$lib/component_scripts/currentOptimizedRoute.svelte.ts';
	import { currentPathState } from '$lib/component_scripts/currentPath.svelte.ts';

	interface Props {
		route: OptimizedRoute;
	}

	const { route }: Props = $props();
	function expandOptimizedRoute() {
		currentRouteState.value = route.route;
		currentOptimizedRouteState.value = route;
		currentPathState.value = route.path;
		goto('/route/optimized');
	}
</script>

<div transition:fade class="flex w-full flex-col rounded-lg bg-white/70 transition hover:bg-white">
	<div class="p-3">
		<img class="h-48 w-full rounded-md object-cover" src={route.route.imageUrl} alt="test-img" />
	</div>
	<div class="mx-3 flex min-h-0 grow flex-col">
		<div class="text-md font-medium text-wrap">
			<TextWithIcon text={route.route.name} icon={() => RouteIcon} />
		</div>
		<div class="my-2 border-t border-gray-300"></div>
		<div class="text-left text-sm font-medium text-gray-500">{route.route.description}</div>
		<div class="mt-2 mb-3 min-h-0 w-full grow">
			<div class="flex h-full w-full items-end justify-end gap-2">
				<button
					onclick={expandOptimizedRoute}
					class="flex justify-center rounded-md border border-transparent bg-blue-100 py-2 pr-2 pl-1 text-sm font-medium text-nowrap text-blue-900 transition hover:bg-blue-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
				>
					<TextWithIcon text="More" icon={() => CircleEllipsis} />
				</button>
			</div>
		</div>
	</div>
</div>
