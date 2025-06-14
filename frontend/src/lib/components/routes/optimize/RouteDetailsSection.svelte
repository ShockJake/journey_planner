<script lang="ts">
	import { fade } from 'svelte/transition';
	import {
		CalendarClock,
		CalendarDays,
		CircleAlert,
		RefreshCw,
		Workflow
	} from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import Loader from '$lib/components/common/Loader.svelte';
	import { currentRouteState } from '$lib/component_scripts/currentRoute.svelte.ts';
	import { currentPathState } from '$lib/component_scripts/currentPath.svelte.ts';
	import RouteDetailsMapSection from './routeDetails/RouteDetailsMapSection.svelte';
	import WeatherSection from './routeDetails/WeatherSection.svelte';
	import type OptimizedRoute from '$lib/types/OptimizedRoute.ts';
	import { optimizeRoute } from '$lib/component_scripts/routeOptimization.ts';
	import Alert from '$lib/components/common/Alert.svelte';
	import { onMount } from 'svelte';
	import { goto } from '$app/navigation';

	const route = currentRouteState.value;
	let optimizedRoute: OptimizedRoute;
	let optimizationInProgress = false;
	let optimizationFinished = false;
	let error = '';
	let startDate = '';
	let startHour = '';

	function finishOptimization() {
		optimizationInProgress = false;
		optimizationFinished = true;
	}

	function displayError(errorValue: string) {
		optimizationInProgress = false;
		optimizationFinished = false;
		error = errorValue;
	}

	function startOptimization() {
		optimizationInProgress = true;
		setTimeout(
			() =>
				optimizeRoute(route.name, startDate, startHour).then((r) => {
					if (typeof r === 'string') {
						displayError(r);
					} else {
						optimizedRoute = r;
						currentRouteState.value = optimizedRoute.route;
						currentPathState.value = optimizedRoute.path;
						console.log(optimizedRoute);
						finishOptimization();
					}
				}),
			1500
		);
	}

	function restartOptimization() {
		error = '';
	}

	onMount(() => {
		if (route.name === '') {
			goto('/route/select');
		}
	});
</script>

<div class="{!optimizationFinished ? 'min-h-0 grow' : ''}  rounded-lg bg-white/30">
	<h2
		class="flex w-full items-center justify-start rounded-lg bg-white/90 py-2 pl-2 text-2xl font-bold text-gray-800 drop-shadow-sm"
	>
		<div class="w-auto">
			<TextWithIcon text="Route Details" icon={() => Workflow} />
		</div>
	</h2>
	{#if !optimizationFinished && !optimizationInProgress && error.length === 0}
		<div
			transition:fade={{ duration: 100 }}
			class="flex h-9/10 w-full flex-col items-center justify-center p-3 text-center text-gray-800"
		>
			<div class="rounded-lg bg-white/70 p-3">
				<div class="mb-3">
					<label for="date" class="mb-1 block text-sm font-medium text-[#07074D]">
						<TextWithIcon text="Start Date" icon={() => CalendarDays} />
					</label>
					<input
						type="date"
						name="date"
						id="date"
						placeholder="10.01.2025"
						bind:value={startDate}
						class="w-full rounded-md border border-[#e0e0e0] bg-white px-6 py-3 text-sm font-light text-[#6B7280] transition outline-none focus:border-green-500"
					/>
				</div>
				<div class="mb-3">
					<label for="time" class="mb-1 block text-sm font-medium text-[#07074D]">
						<TextWithIcon text="Start Hour" icon={() => CalendarClock} />
					</label>
					<input
						type="time"
						name="time"
						id="time"
						placeholder="12:00"
						bind:value={startHour}
						class="w-42 rounded-md border border-[#e0e0e0] bg-white px-6 py-3 text-sm font-light text-[#6B7280] transition outline-none focus:border-green-500"
					/>
				</div>
				<button
					type="button"
					class="w-42 rounded-md bg-green-100 px-4 py-2 text-sm font-medium text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-white/75"
					onclick={startOptimization}>Start Optimization</button
				>
			</div>
		</div>
	{/if}
	{#if error.length !== 0}
		<div
			transition:fade={{ delay: 100, duration: 500 }}
			class="flex h-9/10 w-full flex-col items-center justify-center p-3 text-center text-xl text-gray-800"
		>
			<Alert message={error} type="danger" iconSupplier={() => CircleAlert} />
			<button
				type="button"
				class="mt-3 rounded-md bg-green-100 px-2 py-2 text-sm font-medium text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-white/75"
				onclick={restartOptimization}
				><TextWithIcon text="Try Again" icon={() => RefreshCw} />
			</button>
		</div>
	{/if}

	{#if optimizationInProgress}
		<div
			transition:fade={{ delay: 100, duration: 500 }}
			class="flex h-9/10 w-full flex-col items-center justify-center p-3 text-center text-xl text-gray-800"
		>
			<Loader />
			<span class="text-sm font-medium">Optimization in progress...</span>
		</div>
	{/if}
	{#if optimizationFinished}
		<div transition:fade={{ delay: 100, duration: 800 }} class="m-3 flex flex-col gap-3">
			<RouteDetailsMapSection route={optimizedRoute.route} path={optimizedRoute.path} />
			<WeatherSection weatherInfo={optimizedRoute.weatherInfo} />
		</div>
	{/if}
</div>
