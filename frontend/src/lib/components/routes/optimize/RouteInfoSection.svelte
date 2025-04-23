<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { FileText, Info, Route, MapPinHouse } from '$lib/components/common/Icons.ts';

	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';
	const route = currentRouteState.value;
</script>

<div class="mt-2 w-full rounded-lg bg-white/30">
	<h2
		class="flex w-full items-center justify-start rounded-lg bg-white/90 py-2 pl-2 text-2xl font-bold text-gray-800 drop-shadow-sm"
	>
		<div class="w-auto">
			<TextWithIcon text="Route Info" icon={() => Info} />
		</div>
	</h2>
	<div class="flex flex-col justify-center gap-3 p-3 lg:flex-row">
		<div class="flex w-full items-center justify-center rounded-lg lg:w-auto">
			<img class="h-68 rounded-lg object-cover" src={route.imageUrl} alt="test-img" />
		</div>
		<div class="flex flex-col gap-3 lg:flex-2/3">
			<div class="text-md rounded-lg bg-white px-1 py-2 font-bold">
				<TextWithIcon text={route.name} icon={() => Route} />
			</div>
			<div class="text-md rounded-lg bg-white px-1 py-2 font-bold">
				<TextWithIcon text={currentRouteState.value.municipality} icon={() => MapPinHouse} />
			</div>
			<div class="text-md grow rounded-lg bg-white px-1 py-2 font-medium">
				<TextWithIcon text="Description:" icon={() => FileText} />
				<p class="ml-2 pt-1 text-sm font-medium text-gray-700">{route.description}</p>
			</div>
		</div>
	</div>
</div>
