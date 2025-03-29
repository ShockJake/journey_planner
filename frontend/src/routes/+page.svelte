<script lang="ts">
	import RouteCreation from '$lib/components/RouteCreation.svelte';
	import { TreePalm } from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
</script>

<svelte:head>
	<title>Journey Planner</title>
</svelte:head>

<div class="relative z-10 flex h-full flex-col items-center justify-center text-center text-white">
	<div class="flex h-9/12 w-9/14 flex-col items-center justify-center rounded-lg bg-white/70">
		<h1 class="test-2xl mb-6 font-bold text-gray-800 lg:text-3xl">
			<TextWithIcon text="Welcome to Journey Planner!" icon={() => TreePalm}></TextWithIcon>
		</h1>
		<RouteCreation />
	</div>
</div>
