<script lang="ts">
	import { UserPen } from '$lib/components/common/Icons.ts';
	import ModalRender from '$lib/components/common/ModalRender.svelte';
	import ChangeLoginModalBody from './ChangeLoginModalBody.svelte';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { createDialog } from 'svelte-headlessui';

	const dialog = createDialog({ label: 'authDialog' });
</script>

<button
	on:click={dialog.open}
	class="col-span-1 flex max-w-fit justify-center rounded-md border border-transparent bg-blue-100 py-2 pr-2 pl-1 text-lg font-medium text-nowrap text-blue-900 transition hover:bg-blue-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
>
	<TextWithIcon text="Change login" icon={() => UserPen} /></button
>

<ModalRender body={() => ChangeLoginModalBody} {dialog} />
