<script lang="ts">
	import { Trash2 } from '$lib/components/common/Icons.ts';
	import ModalRender from '$lib/components/common/ModalRender.svelte';
	import DeleteUserModalBody from './DeleteUserModalBody.svelte';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { createDialog } from 'svelte-headlessui';

	const dialog = createDialog({ label: 'authDialog' });
</script>

<button
	on:click={dialog.open}
	class="flex max-w-fit justify-center rounded-md border border-transparent bg-red-100 py-2 pr-2 pl-1 text-sm font-medium text-nowrap text-red-900 transition hover:bg-red-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-red-500 focus-visible:ring-offset-2"
>
	<TextWithIcon text="Delete" icon={() => Trash2} />
</button>

<ModalRender body={() => DeleteUserModalBody} {dialog} />
