<script lang="ts">
	import { CircleEllipsis, Route as RouteIcon } from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { fade } from 'svelte/transition';
	import { goto } from '$app/navigation';
	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';
	import type OptimizedRoute from '$lib/types/OptimizedRoute.ts';
	import { currentOptimizedRouteState } from '$lib/component_scripts/currentOptimizedRoute.svelte.ts';
	import { currentPathState } from '$lib/component_scripts/currentPath.svelte.ts';
	import Button from '$lib/components/common/Button.svelte';

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
		<div class="text-sm font-medium text-wrap lg:text-base">
			<TextWithIcon text={route.route.name} icon={() => RouteIcon} />
		</div>
		<div class="my-2 border-t border-gray-300"></div>
		<div class="text-left text-xs font-medium text-gray-500 lg:text-sm">
			{route.route.description}
		</div>
		<div class="mt-2 mb-3 min-h-0 w-full grow">
			<div class="flex h-full w-full items-end justify-end gap-2">
				<Button
					action={expandOptimizedRoute}
					text="More"
					color="blue"
					iconProvider={() => CircleEllipsis}
				/>
			</div>
		</div>
	</div>
</div>
