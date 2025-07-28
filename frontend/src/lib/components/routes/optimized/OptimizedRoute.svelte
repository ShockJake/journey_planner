<script lang="ts">
	import RouteInfoSection from '../optimize/RouteInfoSection.svelte';
	import Header from './Header.svelte';
	import RouteDetailsMapSection from '../optimize/routeDetails/RouteDetailsMapSection.svelte';
	import { currentOptimizedRouteState } from '$lib/component_scripts/currentOptimizedRoute.svelte.ts';
	import WeatherSection from '../optimize/routeDetails/WeatherSection.svelte';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { Workflow } from '$lib/components/common/Icons.ts';

	const optimizedRoute = currentOptimizedRouteState.value;
</script>

<div class="flex h-full w-full flex-col">
	<Header />
	<div class="flex min-h-0 w-full grow flex-col gap-3 overflow-y-scroll px-3 pb-3">
		<RouteInfoSection />

		<div class="rounded-lg bg-white/30">
			<h2
				class="flex w-full items-center justify-start rounded-lg bg-white/90 py-2 pl-2 text-2xl font-bold text-gray-800 drop-shadow-sm"
			>
				<div class="w-auto">
					<TextWithIcon text="Route Details" icon={() => Workflow} />
				</div>
			</h2>
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
