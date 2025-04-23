<script lang="ts">
	import Header from './Header.svelte';
	import RouteDetailsSection from './RouteDetailsSection.svelte';
	import RouteInfoSection from './RouteInfoSection.svelte';
</script>

<div class="flex h-full w-full flex-col">
	<Header />
	<div class="flex min-h-0 w-full grow flex-col gap-3 overflow-y-scroll px-3 pb-3">
		<RouteInfoSection />
		<RouteDetailsSection />
	</div>
</div>
