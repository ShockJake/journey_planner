<script lang="ts">
	import {
		CircleHelp,
		ClockArrowUp,
		CloudHail,
		CloudRain,
		CloudSnow,
		Droplets,
		Umbrella,
		UmbrellaOff
	} from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import type RainData from '$lib/types/RainData.ts';
	import { fade } from 'svelte/transition';

	let showDescription = $state(false);

	interface Props {
		rainData: RainData;
	}

	const { rainData }: Props = $props();

	function mapIcon(rainIntensity: string) {
		if (rainIntensity === 'LIGHT') return CloudSnow;
		if (rainIntensity === 'MODERATE') return CloudHail;
		return CloudRain;
	}

	function mapText(rainIntensity: string) {
		return rainIntensity.toLowerCase();
	}

	function toggleDescription(value: boolean) {
		showDescription = value;
	}
</script>

<div class="rounded-lg bg-sky-700 p-2 text-sm font-medium text-white">
	<div class="flex flex-row items-center">
		<TextWithIcon
			icon={() => mapIcon(rainData.rainIntensity)}
			text="Rain with {mapText(rainData.rainIntensity)} intensity"
		/>
		<div class="relative inline-block">
			<button
				class="rounded-lg py-1 transition hover:bg-white/10"
				onmouseover={() => toggleDescription(true)}
				onfocus={() => toggleDescription(true)}
				onmouseout={() => toggleDescription(false)}
				onblur={() => toggleDescription(false)}
			>
				<div class="container mx-1 flex items-center">
					<CircleHelp />
				</div>
			</button>
			{#if showDescription}
				<div
					transition:fade
					class="absolute right-0 z-20 w-auto origin-top-right rounded-md bg-white text-nowrap shadow-2xl ring-1 ring-black/5 focus:outline-hidden"
				>
					<div>
						<p class="p-3 text-sm font-extralight text-black">{rainData.rainDescription}</p>
					</div>
				</div>
			{/if}
		</div>
	</div>
	<div class="border-white-300 my-1 border-t"></div>
	<div class="grid grid-cols-2 gap-1 font-normal">
		<div class="flex flex-row items-center">
			<TextWithIcon icon={() => Umbrella} text="Starts -" />
			<p class="font-bold">{rainData.startHour}:00</p>
		</div>
		<div class="flex flex-row items-center">
			<TextWithIcon icon={() => UmbrellaOff} text="Ends -" />
			<p class="font-bold">{rainData.endHour}:00</p>
		</div>
		<div class="flex flex-row items-center">
			<TextWithIcon icon={() => ClockArrowUp} text="Max hour -" />
			<p class="font-bold">{rainData.maxHour}:00</p>
		</div>
		<div class="flex flex-row items-center">
			<TextWithIcon icon={() => Droplets} text="Max amount -" />
			<p class="font-bold">{rainData.maxAmount} mm</p>
		</div>
	</div>
</div>
