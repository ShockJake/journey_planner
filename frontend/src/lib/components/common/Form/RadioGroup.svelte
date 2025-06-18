<script lang="ts">
	import type RadioGroupItem from '$lib/types/RadioGroupItem.ts';
	import TextWithIcon from '../TextWithIcon.svelte';
	let group = $state('startup');

	interface Props {
		items: RadioGroupItem[];
	}
	const { items }: Props = $props();
</script>

{#snippet option(id: string, name: string, description: string, iconProvider: () => any)}
	<div class="relative">
		<input
			{id}
			class="peer absolute h-0 w-0 opacity-0"
			type="radio"
			bind:group
			name="type"
			value={id}
		/>
		<label
			for={id}
			class="relative flex cursor-pointer rounded-lg bg-white px-5 py-4 drop-shadow-lg transition peer-checked:bg-blue-600/75 peer-checked:text-white peer-focus:ring-2 peer-focus:ring-white/60 peer-focus:ring-offset-2 peer-focus:ring-offset-blue-600 focus:outline-hidden [&_p]:text-gray-900 peer-checked:[&_p]:text-white [&_span]:text-gray-500 peer-checked:[&_span]:text-sky-100"
		>
			<div class="flex w-full items-center justify-between">
				<div class="flex items-center justify-start">
					<div class="text-sm">
						<p class="flex justify-start font-medium">
							<TextWithIcon text={name} icon={iconProvider} />
						</p>
						<span class="pl-1">{description}</span>
					</div>
				</div>
				<div class="shrink-0 text-white">
					<svg viewBox="0 0 24 24" fill="none" class="h-6 w-6">
						<circle cx="12" cy="12" r="12" fill="#fff" opacity="0.2" /><path
							d="M7 13l3 3 7-7"
							stroke="#fff"
							stroke-width="1.5"
							stroke-linecap="round"
							stroke-linejoin="round"
						/>
					</svg>
				</div>
			</div>
		</label>
	</div>
{/snippet}

<div class="w-full p-2">
	<div class="mx-auto w-full">
		<fieldset class="flex flex-col space-y-2">
			{#each items as item}
				{@render option(item.value, item.value, item.description, item.iconProvider)}
			{/each}
		</fieldset>
	</div>
</div>
