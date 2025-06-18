<script lang="ts">
	import { createSwitch } from 'svelte-headlessui';

	interface Props {
		label: string;
		toggleFunction: () => void;
	}

	const { label, toggleFunction }: Props = $props();
	const sw = createSwitch({ label: label });
</script>

<button
	class="{$sw.checked
		? 'bg-green-400'
		: 'bg-gray-300'} relative inline-flex h-[18px] w-[36px] shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out focus:outline-hidden focus-visible:ring-2 focus-visible:ring-white/75"
	use:sw.toggle
	onclick={toggleFunction}
>
	<span class="sr-only">ignored</span>
	<span
		aria-hidden="true"
		class="{$sw.checked
			? 'translate-x-4.5'
			: 'translate-x-0'} pointer-events-none inline-block h-[14px] w-[14px] transform rounded-full bg-white shadow-lg ring-0 transition duration-200 ease-in-out"
	></span>
</button>
