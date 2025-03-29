<script lang="ts">
	import { CircleUserRound } from '$lib/components/common/Icons.ts';
	import Modal from '$lib/components/common/Modal.svelte';
	import AuthModalBody from './AuthModalBody.svelte';
	import { createDialog } from 'svelte-headlessui';

	const dialog = createDialog({ label: 'authDialog' });
</script>

<button class="rounded-lg px-2 py-2 transition hover:bg-white/30" onclick={dialog.open}>
	<div class="container mx-1 flex items-center">
		<CircleUserRound />
	</div>
</button>

<Modal modalBody={() => AuthModalBody} {dialog} />
