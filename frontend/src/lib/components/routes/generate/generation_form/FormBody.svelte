<script lang="ts">
	import { isAuthenticated } from '$lib/component_scripts/authentication.ts';
	import {
		MapPinHouse,
		LandPlot,
		Waypoints,
		Save,
		Dot,
		Ellipsis,
		GripHorizontal
	} from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import type ComboboxItem from '$lib/types/ComboboxItem.ts';
	import Combobox from '$lib/components/common/Form/Combobox.svelte';
	import ComboboxMulti from '$lib/components/common/Form/ComboboxMulti.svelte';
	import RadioGroup from '$lib/components/common/Form/RadioGroup.svelte';
	import Toggle from '$lib/components/common/Form/Toggle.svelte';
	import type RadioGroupItem from '$lib/types/RadioGroupItem.ts';

	interface Props {
		municipalities: { name: string }[];
		placeTypes: ComboboxItem[];
	}

	const { municipalities, placeTypes }: Props = $props();
	let saveToAccount: boolean = $state(false);
	let chosenMunicipality: string = '';
	let chosenPlaceTypes: ComboboxItem[] = [];
	const radioGroupItems: RadioGroupItem[] = [
		{ value: 'Short', description: 'Up to 4 places', iconProvider: () => Dot },
		{ value: 'Normal', description: 'Up to 8 places', iconProvider: () => Ellipsis },
		{ value: 'Long', description: 'Up to 16 places', iconProvider: () => GripHorizontal }
	];

	function toggleSaveToAccount() {
		saveToAccount = !saveToAccount;
	}

	function changeChosenMunicipality(municipality: string) {
		chosenMunicipality = municipality;
	}

	function changeChosenPlaceTypes(placeTypes: ComboboxItem[]) {
		chosenPlaceTypes = placeTypes;
	}

	function triggerGeneration() {
		console.log('Generation triggered');
	}
</script>

<div
	class="flex w-full grow flex-col items-center justify-center rounded-lg bg-white/40 text-black"
>
	<div class="flex w-full grow flex-col items-center gap-2 p-2">
		<div class="flex w-full items-center gap-2 rounded-lg bg-white/80 p-2">
			<div class="flex w-full flex-1/2 flex-row items-center gap-1">
				<TextWithIcon text="Place" icon={() => MapPinHouse} />
				<Combobox data={municipalities} callback={changeChosenMunicipality} />
			</div>
			{#if $isAuthenticated}
				<div
					class="flex flex-row items-center gap-1 rounded-lg p-2 transition {saveToAccount
						? ''
						: 'bg-gray-100/60'}"
				>
					<div class="{saveToAccount ? 'text-black' : 'text-gray-600'} text-sm">
						<TextWithIcon text="Save to account" icon={() => Save} />
					</div>
					<Toggle label="SaveToAccount" toggleFunction={toggleSaveToAccount} />
				</div>
			{/if}
		</div>
		<div class="flex w-full flex-col gap-1 rounded-lg bg-white/80 p-2">
			<TextWithIcon text="Route Longevity" icon={() => Waypoints} />
			<RadioGroup items={radioGroupItems} />
		</div>
		<div class="flex w-full flex-col gap-1 rounded-lg bg-white/80 p-2">
			<TextWithIcon text="Attraction Selection" icon={() => LandPlot} />
			<ComboboxMulti data={placeTypes} callback={changeChosenPlaceTypes} />
		</div>
	</div>
	<div class="flex w-full">
		<div class="m-2 flex w-full">
			<button
				onclick={triggerGeneration}
				class="inline-flex w-full justify-center rounded-md border border-transparent bg-green-100 px-4 py-2 text-sm font-medium text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-green-500 focus-visible:ring-offset-2"
				>Generate</button
			>
		</div>
	</div>
</div>
