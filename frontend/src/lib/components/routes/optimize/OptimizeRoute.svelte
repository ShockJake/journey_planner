<script lang="ts">
	import { MapPinPlusInside, Route } from '$lib/components/common/Icons.ts';
	import PageHeader from '$lib/components/common/PageHeader.svelte';
	import RouteDetailsSection from './RouteDetailsSection.svelte';
	import RouteInfoSection from './RouteInfoSection.svelte';
</script>

<div class="flex h-full w-full flex-col">
	<PageHeader
		text="Route Optimzation"
		firstIcon={() => Route}
		secondIcon={() => MapPinPlusInside}
	/>
	<div class="flex min-h-0 w-full grow flex-col gap-3 px-3 pb-3">
		<RouteInfoSection />
		<RouteDetailsSection />
	</div>
</div>
