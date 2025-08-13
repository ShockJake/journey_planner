<script lang="ts">
	import { Trash2 } from '$lib/components/common/Icons.ts';
	import ModalRender from '$lib/components/common/ModalRender.svelte';
	import DeleteUserModalBody from './DeleteUserModalBody.svelte';
	import { createDialog } from 'svelte-headlessui';
	import ButtonShiftingWidth from '$lib/components/common/ButtonShiftingWidth.svelte';

	const dialog = createDialog({ label: 'authDialog' });
</script>

<ButtonShiftingWidth action={dialog.open} color="red" text="Delete" iconProvider={() => Trash2} />

<ModalRender body={() => DeleteUserModalBody} {dialog} />
