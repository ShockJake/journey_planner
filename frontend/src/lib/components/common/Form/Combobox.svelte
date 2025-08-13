<script lang="ts">
	import { createCombobox } from 'svelte-headlessui';
	import Transition from 'svelte-transition';
	import { ChevronsUpDown, Check } from '../Icons.ts';

	interface Props {
		data: {
			name: string;
		}[];
		callback: (value: string) => void;
	}
	let { data, callback }: Props = $props();

	const combobox = createCombobox({ label: 'Data', selected: data[0] });

	function onChange(e: Event) {
		if ((e as CustomEvent).detail.selected !== undefined) {
			const value = $state.snapshot((e as CustomEvent).detail.selected).name;
			callback(value);
		}
	}

	let filtered = $derived(
		data.filter((item) =>
			item.name
				.toLowerCase()
				.replace(/\s+/g, '')
				.includes($combobox.filter.toLowerCase().replace(/\s+/g, ''))
		)
	);
</script>

<div class="relative">
	<div
		class="relative w-full cursor-default overflow-hidden rounded-lg border border-gray-200 bg-white text-left transition hover:bg-gray-100"
	>
		<input
			use:combobox.input
			onchange={onChange}
			class="w-full p-2 leading-5 text-gray-900 focus:outline-hidden"
			value={$combobox.selected?.name ?? ''}
		/>
		<button
			use:combobox.button
			class="absolute inset-y-0 right-0 flex items-center pr-1"
			type="button"
		>
			<ChevronsUpDown class="h-5 text-gray-400" />
		</button>
	</div>

	<Transition
		show={$combobox.expanded}
		enter="ease-out duration-300"
		enterFrom="opacity-0"
		enterTo="opacity-100"
		leave="transition ease-in duration-200"
		leaveFrom="opacity-100"
		leaveTo="opacity-0"
		on:after-leave={() => combobox.reset()}
	>
		<ul
			use:combobox.items
			class="absolute z-10 mt-1 max-h-60 w-full overflow-auto rounded-md bg-white shadow-lg transition focus:outline-hidden"
		>
			{#each filtered as value}
				{@const active = $combobox.active === value}
				{@const selected = $combobox.selected === value}
				<li
					class="relative cursor-default py-2 transition select-none {active
						? 'bg-green-100'
						: 'text-gray-900'}"
					use:combobox.item={{ value }}
				>
					<span class="block truncate text-center {selected ? 'font-medium' : 'font-normal'}"
						>{value.name}</span
					>
					{#if selected}
						<span
							class="absolute inset-y-0 left-0 flex items-center pl-2 transition {active
								? 'text-black'
								: 'text-teal-600'}"
						>
							<Check class="h-5" />
						</span>
					{/if}
				</li>
			{:else}
				<li class="relative cursor-default select-none py-2 text-gray-900">
					<span class="block truncate font-normal">Nothing found</span>
				</li>
			{/each}
		</ul>
	</Transition>
</div>
