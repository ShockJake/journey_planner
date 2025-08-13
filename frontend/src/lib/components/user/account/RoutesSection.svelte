<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { Route as RouteIcon } from '$lib/components/common/Icons.ts';
	import type Route from '$lib/types/Route.ts';
	import { fade } from 'svelte/transition';
	import { createDialog } from 'svelte-headlessui';
	import MoreModalBody from '$lib/components/routes/select/MoreModalBody.svelte';
	import ModalRenderSized from '$lib/components/common/ModalRenderSized.svelte';
	import type DialogInterface from '$lib/components/common/DialogInterface.ts';
	import RouteSection from '$lib/components/routes/select/RouteSection.svelte';

	interface Props {
		routes: Route[];
	}

	const dialog: DialogInterface = createDialog({ label: 'moreInfoModal' });
	const { routes }: Props = $props();
</script>

<div class="{routes.length > 0 ? '' : 'min-h-0 grow '} rounded-lg bg-white/70">
	<h2
		class="flex w-full items-center justify-center rounded-lg bg-white/80 py-2 text-lg font-bold text-gray-800 drop-shadow-md lg:text-2xl"
	>
		<div class="w-auto">
			<TextWithIcon text="Saved Routes" icon={() => RouteIcon} />
		</div>
	</h2>
	{#if routes.length === 0}
		<div
			class="flex h-9/10 w-full items-center justify-center p-3 text-center text-sm text-gray-800 lg:text-xl"
		>
			Nothing to show
		</div>
	{:else}
		<div
			in:fade
			class="grid w-full grid-cols-1 gap-2 overflow-scroll p-3 md:grid-cols-2 lg:grid-cols-3"
		>
			{#each routes as route}
				<RouteSection {route} showDialogAction={dialog.open} />
			{/each}
		</div>
	{/if}
</div>

<ModalRenderSized body={() => MoreModalBody} {dialog} height="h-full" width="w-8/10" />
