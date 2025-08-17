<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { FileText, Info, Route, MapPinHouse } from '$lib/components/common/Icons.ts';

	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';
	import SectionHeader from '$lib/components/common/SectionHeader.svelte';
	const route = currentRouteState.value;
</script>

<div class="mt-2 w-full rounded-lg bg-white/30">
	<SectionHeader text="Route Info" icon={() => Info} />

	<div class="flex flex-col justify-center gap-3 p-3 lg:flex-row">
		<div class="flex w-full items-center justify-center rounded-lg lg:w-auto">
			<img class="h-68 rounded-lg object-cover" src={route.imageUrl} alt="test-img" />
		</div>
		<div class="flex flex-col gap-3 lg:flex-2/3">
			<div class="rounded-lg bg-white px-1 py-2 text-sm font-bold lg:text-base">
				<TextWithIcon text={route.name} icon={() => Route} />
			</div>
			<div class="rounded-lg bg-white px-1 py-2 text-sm font-bold lg:text-base">
				<TextWithIcon text={currentRouteState.value.municipality} icon={() => MapPinHouse} />
			</div>
			<div class="grow rounded-lg bg-white px-1 py-2 text-sm font-medium lg:text-base">
				<TextWithIcon text="Description:" icon={() => FileText} />
				<p class="ml-2 pt-1 text-xs font-medium text-gray-700 lg:text-sm">{route.description}</p>
			</div>
		</div>
	</div>
</div>
