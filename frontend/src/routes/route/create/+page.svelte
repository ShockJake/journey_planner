<script>
	import GenerateRoute from '$lib/components/routes/generate/GenerateRoute.svelte';
	import { fade } from 'svelte/transition';
</script>

<svelte:head>
	<title>Route Creation</title>
</svelte:head>

<div
	in:fade
	class="relative z-10 flex h-full flex-col items-center justify-center text-center text-white"
>
	<div
		class="flex h-9/10 w-9/10 items-center justify-center rounded-lg bg-white/65 lg:h-8/10 lg:w-1/2"
	>
		<GenerateRoute />
	</div>
</div>
