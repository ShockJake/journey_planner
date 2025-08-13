<script lang="ts">
	import { Wind } from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import type WindData from '$lib/types/WindData.ts';
	import { fade } from 'svelte/transition';

	interface Props {
		windData: WindData;
	}
	const { windData }: Props = $props();
	let showDescription = $state(false);

	function prepateWindAvgSpeed(windAvgSpeed: number) {
		return windAvgSpeed.toFixed(2);
	}

	function prepareWindIntensity(windIntensity: string) {
		return windIntensity.toLowerCase().replace('_', ' ');
	}

	function toggleDescription(value: boolean) {
		showDescription = value;
	}
</script>

<div class="flex flex-col rounded-lg bg-gray-300 p-2">
	<div class="flex items-center justify-center">
		<h4 class="text-gray w-auto text-sm font-medium lg:text-base">
			<TextWithIcon text="Wind" icon={() => Wind} />
		</h4>
	</div>
	<div class="border-black-300 mt-1 mb-2 border-t"></div>
	<div
		class="flex h-full flex-col items-center justify-center gap-2 rounded-lg bg-gray-500 p-2 text-sm font-medium text-white"
	>
		<div class="text-lg font-bold lg:text-2xl">
			<TextWithIcon text="{prepateWindAvgSpeed(windData.averageSpeed)} km/h" icon={() => Wind} />
		</div>
		<div class="flex flex-row items-center justify-center text-xs font-medium lg:text-sm">
			<p>Intensity:</p>

			<div class="relative inline-block">
				<button
					class="rounded-lg p-1 transition hover:bg-white/10"
					onmouseover={() => toggleDescription(true)}
					onfocus={() => toggleDescription(true)}
					onmouseout={() => toggleDescription(false)}
					onblur={() => toggleDescription(false)}
				>
					<p class="font-bold underline">{prepareWindIntensity(windData.intensity)}</p>
				</button>
				{#if showDescription}
					<div
						transition:fade
						class="absolute -right-14.5 z-20 w-auto origin-top-right rounded-md bg-white text-nowrap shadow-2xl ring-1 ring-black/5 focus:outline-hidden"
					>
						<div>
							<p class="p-3 text-xs font-extralight text-black lg:text-sm">
								{windData.description}
							</p>
						</div>
					</div>
				{/if}
			</div>
		</div>
	</div>
</div>
