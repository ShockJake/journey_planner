<script lang="ts">
	import RouteInfoSection from '../optimize/RouteInfoSection.svelte';
	import RouteDetailsMapSection from '../optimize/routeDetails/RouteDetailsMapSection.svelte';
	import { currentOptimizedRouteState } from '$lib/component_scripts/currentOptimizedRoute.svelte.ts';
	import WeatherSection from '../optimize/routeDetails/WeatherSection.svelte';
	import { MapPinPlusInside, Route, Workflow } from '$lib/components/common/Icons.ts';
	import PageHeader from '$lib/components/common/PageHeader.svelte';
	import SectionHeader from '$lib/components/common/SectionHeader.svelte';

	const optimizedRoute = currentOptimizedRouteState.value;
</script>

<div class="flex h-full w-full flex-col">
	<PageHeader text="Optimized Route " firstIcon={() => Route} secondIcon={() => MapPinPlusInside} />
	<div class="flex min-h-0 w-full grow flex-col gap-3 overflow-y-scroll px-3 pb-3">
		<RouteInfoSection />
		<div class="rounded-lg bg-white/30">
			<SectionHeader text="Route Details" icon={() => Workflow} />
			<div class="m-3 flex flex-col gap-3">
				<RouteDetailsMapSection
					optimizationId={optimizedRoute.optimizationId}
					route={optimizedRoute.route}
					path={optimizedRoute.path}
				/>
				<WeatherSection weatherInfo={optimizedRoute.weatherInfo} />
			</div>
		</div>
	</div>
</div>
