<script lang="ts">
	import { KeyRound } from '$lib/components/common/Icons.ts';
	import ModalRender from '$lib/components/common/ModalRender.svelte';
	import ChangePasswordModalBody from './ChangePasswordModalBody.svelte';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { createDialog } from 'svelte-headlessui';

	const dialog = createDialog({ label: 'authDialog' });
</script>

<button
	on:click={dialog.open}
	class="flex max-w-fit justify-center rounded-md border border-transparent bg-cyan-100 py-2 pr-2 pl-1 text-sm font-medium text-nowrap text-cyan-900 transition hover:bg-cyan-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-cyan-500 focus-visible:ring-offset-2"
>
	<TextWithIcon text="Change Password" icon={() => KeyRound} />
</button>

<ModalRender body={() => ChangePasswordModalBody} {dialog} />
