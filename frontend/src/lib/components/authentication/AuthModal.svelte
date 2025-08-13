<script lang="ts">
	import { CircleUserRound } from '$lib/components/common/Icons.ts';
	import Modal from '$lib/components/common/Modal.svelte';
	import AuthModalBody from './AuthModalBody.svelte';
	import { createDialog } from 'svelte-headlessui';

	interface Props {
		isWide: boolean;
	}

	const { isWide }: Props = $props();

	const dialog = createDialog({ label: 'authDialog' });
</script>

{#if isWide}
	<button
		class="cursor-pointer rounded-lg px-2 py-2 transition hover:bg-white/30"
		onclick={dialog.open}
	>
		<div class="container mx-1 flex items-center">
			<CircleUserRound />&nbsp;Account
		</div>
	</button>
{:else}
	<button
		class="cursor-pointer rounded-lg px-2 py-2 transition hover:bg-white/30"
		onclick={dialog.open}
	>
		<div class="container mx-1 flex items-center">
			<CircleUserRound />
		</div>
	</button>
{/if}

<Modal modalBody={() => AuthModalBody} {dialog} />
