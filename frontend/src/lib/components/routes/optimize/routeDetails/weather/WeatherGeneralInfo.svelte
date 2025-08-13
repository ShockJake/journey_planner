<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import {
		ThermometerSun,
		ThermometerSnowflake,
		Thermometer,
		Wind
	} from '$lib/components/common/Icons.ts';
	import type GeneralWeatherData from '$lib/types/GeneralWeatherData.ts';

	interface Props {
		generalData: GeneralWeatherData;
	}

	const { generalData }: Props = $props();
</script>

<div class="rounded-lg bg-green-300 p-2">
	<div class="flex w-full items-center justify-center">
		<h4 class="text-gray w-auto text-sm font-medium lg:text-base">
			<TextWithIcon text="General" icon={() => ThermometerSun} />
		</h4>
	</div>
	<div class="border-black-300 mt-1 mb-2 border-t"></div>
	<div class="flex items-center justify-center rounded-lg bg-emerald-400 p-2">
		<table class="text-xs font-medium lg:text-sm">
			<thead></thead>
			<tbody>
				<tr
					><td>
						<TextWithIcon text="Temperature avg." icon={() => Thermometer} />
					</td>
					<td>
						<div class="flex w-auto justify-end font-bold">
							<p>{generalData.meanTemperature} °C</p>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<TextWithIcon text="Temperature min" icon={() => ThermometerSnowflake} />
					</td>
					<td>
						<div class="flex w-auto justify-end font-bold">
							<p>{generalData.minTemperature} °C</p>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<TextWithIcon text="Temperature max" icon={() => ThermometerSun} />
					</td>
					<td>
						<div class="flex w-auto justify-end font-bold">
							<p class="justify-end">{generalData.maxTemperature} °C</p>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<TextWithIcon text="Wind speed max" icon={() => Wind} />
					</td>
					<td>
						<div class="flex w-auto justify-end font-bold">
							<p class="justify-end">{generalData.maxWindSpeed} km/h</p>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
