<script>
	import GenerateRoute from '$lib/components/routes/generate/GenerateRoute.svelte';
</script>

<svelte:head>
	<title>Route Creation</title>
</svelte:head>

<div class="relative z-10 flex h-full flex-col items-center justify-center text-center text-white">
	<div class="flex h-9/10 w-9/10 items-center justify-center rounded-lg bg-white/65">
		<GenerateRoute />
	</div>
</div>
