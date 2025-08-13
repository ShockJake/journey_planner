<script lang="ts">
	import type { Church } from './Icons.ts';
	import TestWithTwoIcons from './TestWithTwoIcons.svelte';
	import TextWithIcon from './TextWithIcon.svelte';

	interface Props {
		text: string;
		firstIcon: () => typeof Church;
		secondIcon: () => typeof Church | undefined;
	}
	const { text, firstIcon, secondIcon }: Props = $props();
</script>

<h1
	class="flex w-full flex-row items-center justify-center rounded-lg bg-white py-2 text-xl font-bold text-gray-800 drop-shadow-md lg:text-3xl"
>
	<div class="w-auto">
		{#if secondIcon === undefined}
			<TextWithIcon {text} icon={firstIcon} />
		{:else}
			<TestWithTwoIcons {text} {firstIcon} {secondIcon} />
		{/if}
	</div>
</h1>
