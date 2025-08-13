<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { CloudSun } from '$lib/components/common/Icons.ts';
	import type WeatherInfo from '$lib/types/WeatherInfo.ts';
	import WeatherGeneralInfo from './weather/WeatherGeneralInfo.svelte';
	import RainInfo from './weather/RainInfo.svelte';
	import WindInfo from './weather/WindInfo.svelte';

	interface WeatherPropos {
		weatherInfo: WeatherInfo;
	}

	const { weatherInfo }: WeatherPropos = $props();
</script>

<div class="flex w-full flex-col gap-2 rounded-lg bg-white/90 p-3">
	<h3 class="text-lg font-bold lg:text-2xl">
		<TextWithIcon text="Weather" icon={() => CloudSun} />
	</h3>
	<div class="flex w-full flex-col gap-2 lg:flex-row">
		<WeatherGeneralInfo generalData={weatherInfo.generalData} />
		<RainInfo rainData={weatherInfo.rainData} />
		<WindInfo windData={weatherInfo.windData} />
	</div>
</div>
