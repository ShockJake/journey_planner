<script lang="ts">
	import '../app.css';
	import NavBar from '$lib/components/NavBar.svelte';
	let { children } = $props();
</script>

<div class="relative h-screen w-full overflow-hidden">
	<div
		class="absolute inset-0 bg-cover bg-center bg-no-repeat"
		style="background-image: url('/images/background.webp');"
	></div>
	<div class="absolute inset-0 bg-white/1 backdrop-blur-sm"></div>
	<div class="relative z-10 flex h-full flex-col">
		<NavBar />
		{@render children()}
	</div>
</div>
