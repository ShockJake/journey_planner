<script>
	import SelectRoute from '$lib/components/routes/select/SelectRoute.svelte';
	import { fade } from 'svelte/transition';
</script>

<svelte:head>
	<title>Route Selection</title>
</svelte:head>

<div in:fade class="relative z-10 m-3 flex h-full flex-col items-center">
	<div class="flex h-9/10 w-9/10 items-center justify-center rounded-lg bg-white/65">
		<SelectRoute />
	</div>
</div>
