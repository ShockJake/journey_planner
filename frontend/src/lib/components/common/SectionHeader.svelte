<script lang="ts">
	import { Info } from './Icons.ts';
	import TextWithIcon from './TextWithIcon.svelte';

	interface Props {
		text: string;
		icon: () => typeof Info;
	}
	const { text, icon }: Props = $props();
</script>

<h2
	class="flex w-full items-center justify-start rounded-lg bg-white/90 py-2 pl-2 text-lg font-bold text-gray-800 drop-shadow-sm lg:text-2xl"
>
	<div class="w-auto">
		<TextWithIcon {text} {icon} />
	</div>
</h2>
