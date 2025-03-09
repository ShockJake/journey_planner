<script lang="ts">
	import RouteCreation from '$lib/components/RouteCreation.svelte';
</script>

<svelte:head>
	<title>Journey Planner</title>
</svelte:head>

<div class="relative z-10 flex h-full flex-col items-center justify-center text-center text-white">
	<div class=" flex h-9/12 w-9/14 flex-col items-center justify-center rounded-lg bg-white/65">
		<h1 class="mb-6 text-3xl font-bold text-gray-800">Welcome to Journey Planner!</h1>
		<RouteCreation />
	</div>
</div>
