<script lang="ts">
	import Map from '$lib/components/common/Map.svelte';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { LandPlot, List } from '$lib/components/common/Icons.ts';
	import type Route from '$lib/types/Route.ts';
	import { getIcon } from '$lib/component_scripts/placeIconMapper.ts';

	interface Props {
		route: Route;
	}
	const { route }: Props = $props();
</script>

<div class="flex w-full flex-col gap-3 rounded-lg bg-white/90 p-3">
	<h3 class="text-2xl font-bold">
		<TextWithIcon text="Plan" icon={() => LandPlot} />
	</h3>
	<div class="flex flex-col lg:flex-row">
		<div
			style="height: 500px; width: 500px"
			class="flex-none rounded-lg bg-gray-50 p-2 lg:flex-1/3"
		>
			<Map />
		</div>
		<div class="px-2 lg:flex-2/3">
			<h3 class="text-md pt-2 pb-1 font-bold">
				<TextWithIcon text="Key Points:" icon={() => List} />
			</h3>
			<div class="pt-1 pl-1 text-sm font-medium text-gray-600">
				<ul class="flex flex-col gap-1">
					{#each route.places as place}
						<li>
							<TextWithIcon text={place.name} icon={() => getIcon(place.placeType)} />
						</li>
					{/each}
				</ul>
			</div>
		</div>
	</div>
</div>
