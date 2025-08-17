<script lang="ts">
	import type { Church } from '../Icons.ts';
	import TextWithIcon from '../TextWithIcon.svelte';

	interface Props {
		color: string;
		iconProvider: (() => typeof Church) | undefined;
		text: string;
		action: () => void;
		width: string | undefined;
		style?: string;
	}
	const { style = '', color, iconProvider, text, action, width }: Props = $props();
</script>

<button
	onclick={action}
	class="{width !== undefined
		? width
		: ''} flex {style} cursor-pointer items-center justify-center rounded-md border border-transparent duration-300 ease-in-out bg-{color}-100 text-xs font-medium text-nowrap lg:text-sm text-{color}-900 transition hover:bg-{color}-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-{color}-500 focus-visible:ring-offset-2"
>
	{#if iconProvider === undefined}
		<div class="w-full items-center justify-center p-2 text-center">
			{text}
		</div>
	{:else}
		<div class="py-2 pr-2 pl-1">
			<TextWithIcon {text} icon={iconProvider} />
		</div>
	{/if}
</button>
