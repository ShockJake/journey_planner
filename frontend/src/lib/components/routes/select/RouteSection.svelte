<script lang="ts">
	import { Route, SquareCheck, CircleEllipsis } from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';
	import { goto } from '$app/navigation';
	import { fade } from 'svelte/transition';
	import Button from '$lib/components/common/buttons/Button.svelte';

	let { showDialogAction, route } = $props();

	function showDialog() {
		currentRouteState.value = route;
		setTimeout(() => dispatchEvent(new Event('resize')), 10);
		showDialogAction();
	}

	function gotoRouteOptimization() {
		currentRouteState.value = route;
		goto('/route/optimize');
	}
</script>

<div transition:fade class="flex w-full flex-col rounded-lg bg-white/70 transition hover:bg-white">
	<div class="p-3">
		<img class="h-48 w-full rounded-md object-cover" src={route.imageUrl} alt="test-img" />
	</div>
	<div class="mr-3 ml-5 flex min-h-0 grow flex-col">
		<div class="lg:text-2sm text-sm font-medium text-wrap">
			<TextWithIcon text={route.name} icon={() => Route} />
		</div>
		<div class="my-2 border-t border-gray-300"></div>
		<div class="text-xs font-medium text-gray-500 lg:text-sm">{route.description}</div>
		<div class="mt-2 mb-3 min-h-0 w-full grow">
			<div class="flex h-full w-full items-end justify-end gap-2">
				<Button color="blue" action={showDialog} iconProvider={() => CircleEllipsis} text="More" />
				<Button
					color="green"
					action={gotoRouteOptimization}
					text="Quick Select"
					iconProvider={() => SquareCheck}
				/>
			</div>
		</div>
	</div>
</div>
