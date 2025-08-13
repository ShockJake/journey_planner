<script lang="ts">
	import { createCombobox } from 'svelte-headlessui';
	import Transition from 'svelte-transition';
	import { Check, ChevronsUpDown, CircleX } from '../Icons.ts';
	import { fade } from 'svelte/transition';
	import type ComboboxItem from '$lib/types/ComboboxItem.ts';

	interface Props {
		data: ComboboxItem[];
		callback: (values: ComboboxItem[]) => void;
	}
	let { data, callback }: Props = $props();
	const combobox = createCombobox({ label: 'ComboboxMulti', selected: [] });

	function onChange(e: Event) {
		callback((e as CustomEvent).detail.selected);
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

<div class="relative mt-1">
	<span class="inline-block w-full rounded-md shadow-xs">
		<button
			use:combobox.button
			onchange={onChange}
			class="focus:shadow-outline-teal relative w-full cursor-default rounded-md border border-gray-300 bg-white py-2 pr-10 pl-2 text-left transition duration-150 ease-in-out hover:bg-gray-50 focus:border-teal-300 focus:outline-hidden sm:leading-5"
		>
			<div class="flex flex-wrap gap-2">
				{#each $combobox.selected as selected (selected.id)}
					<span in:fade out:fade class="flex items-center gap-1 rounded-sm bg-blue-50 px-2 py-0.5">
						<span>{selected.name}</span>
						<div
							class="rounded-lg py-1 transition hover:bg-blue-300/30"
							use:combobox.deselect={selected}
						>
							<CircleX class="h-4" />
						</div>
					</span>
				{:else}
					<span class="flex items-center gap-1 rounded-sm px-2 py-0.5"> Empty </span>
				{/each}
				<input
					use:combobox.input
					onchange={onChange}
					placeholder="Search&hellip;"
					class="w-auto border-none py-1 leading-5 text-gray-900 focus:ring-0 focus:outline-hidden"
				/>
			</div>
			<span class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-2">
				<ChevronsUpDown class="h-5 w-5 text-gray-400" />
			</span>
		</button>
	</span>

	<Transition
		show={$combobox.expanded}
		enter="ease-out duration-300"
		enterFrom="opacity-0"
		enterTo="opacity-100"
		leave="transition ease-in duration-100"
		leaveFrom="opacity-100"
		leaveTo="opacity-0"
	>
		<ul
			use:combobox.items
			class="absolute mt-1 max-h-60 w-full overflow-auto rounded-md bg-white shadow-lg ring-1 ring-black/5 focus:outline-hidden"
		>
			{#each filtered as value}
				{@const active = $combobox.active === value}
				{@const selected = $combobox.selected.includes(value)}
				<li
					class="relative cursor-default py-2 pr-9 pl-4 select-none focus:outline-hidden {active
						? 'bg-green-200'
						: 'text-gray-900'}"
					use:combobox.item={{ value }}
				>
					{#if selected}
						<span class="absolute inset-y-0 right-0 flex items-center pr-3 text-teal-600">
							<Check class="h-4" />
						</span>
					{/if}
					<span class="block truncate {selected ? 'font-semibold' : 'font-normal'}"
						>{value.name}</span
					>
				</li>
			{:else}
				<li class="relative cursor-default select-none py-2 pl-10 pr-4 text-gray-900">
					<span class="block truncate font-normal">Nothing found</span>
				</li>
			{/each}
		</ul>
	</Transition>
</div>
