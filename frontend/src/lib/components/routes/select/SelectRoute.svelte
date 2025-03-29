<script lang="ts">
	import Header from './Header.svelte';
	import RouteSection from './RouteSection.svelte';
</script>

<div class="flex h-full w-full flex-col">
	<Header />
	<div class="flex min-h-0 w-full grow flex-row">
		<div class="grid w-full grid-cols-1 gap-2 overflow-scroll p-3 md:grid-cols-2 lg:grid-cols-3">
			<RouteSection />
			<RouteSection />
			<RouteSection />
			<RouteSection />
			<RouteSection />
			<RouteSection />
		</div>
	</div>
</div>
