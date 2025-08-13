<script lang="ts">
	import type { Church } from '../Icons.ts';
	import TextWithIcon from '../TextWithIcon.svelte';

	interface Props {
		color: string;
		iconProvider: () => typeof Church;
		text: string;
		action: () => void;
	}
	const { color, iconProvider, text, action }: Props = $props();
</script>

<button
	onclick={action}
	class="flex cursor-pointer justify-center rounded-md border border-transparent bg-{color}-100 py-2 pr-2 pl-1 text-xs font-medium text-nowrap lg:text-sm text-{color}-900 transition hover:bg-{color}-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-{color}-500 focus-visible:ring-offset-2"
>
	<TextWithIcon {text} icon={iconProvider} />
</button>
