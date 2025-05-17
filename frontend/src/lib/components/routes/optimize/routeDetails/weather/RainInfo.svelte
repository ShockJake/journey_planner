<script lang="ts">
	import { CloudSunRain, Laugh } from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import type RainData from '$lib/types/RainData.ts';
	import RainMiniSection from './RainMiniSection.svelte';

	interface Props {
		rainData: RainData[];
	}

	const { rainData }: Props = $props();
</script>

<div class="flex flex-col rounded-lg bg-blue-300 p-2">
	<div class="flex items-center justify-center">
		<h4 class="text-gray text-md w-auto font-medium">
			<TextWithIcon text="Rain" icon={() => CloudSunRain} />
		</h4>
	</div>
	<div class="border-black-300 mt-1 mb-2 border-t"></div>
	<div class="flex h-full flex-col items-center justify-center gap-2 lg:flex-row">
		{#if rainData.length === 0}
			<TextWithIcon text="No rain detected" icon={() => Laugh} />
		{:else}
			{#each rainData as data}
				<RainMiniSection rainData={data} />
			{/each}
		{/if}
	</div>
</div>
