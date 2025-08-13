<script lang="ts">
	import RouteCreation from '$lib/components/RouteCreation.svelte';
	import { TreePalm } from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { fade } from 'svelte/transition';
</script>

<svelte:head>
	<title>Journey Planner</title>
</svelte:head>

<div
	in:fade
	class="relative z-10 flex h-full flex-col items-center justify-center text-center text-white"
>
	<div
		class="flex h-9/12 w-9/10 flex-col items-center justify-center rounded-lg bg-white/70 lg:w-9/14"
	>
		<h1 class="mb-6 text-sm font-bold text-gray-800 lg:text-3xl">
			<TextWithIcon text="Welcome to Journey Planner!" icon={() => TreePalm}></TextWithIcon>
		</h1>
		<RouteCreation />
	</div>
</div>
