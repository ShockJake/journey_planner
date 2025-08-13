<script>
	import GenerateRoute from '$lib/components/routes/generate/GenerateRoute.svelte';
	import { fade } from 'svelte/transition';
</script>

<svelte:head>
	<title>Route Creation</title>
</svelte:head>

<div in:fade class="relative z-10 m-3 flex h-full flex-col items-center overflow-y-scroll">
	<div
		class="flex h-99/100 w-99/100 items-center justify-center rounded-lg bg-white/65 sm:w-9/10 lg:w-1/2"
	>
		<GenerateRoute />
	</div>
</div>
