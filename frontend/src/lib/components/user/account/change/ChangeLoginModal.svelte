<script lang="ts">
	import { UserPen } from '$lib/components/common/Icons.ts';
	import ModalRender from '$lib/components/common/ModalRender.svelte';
	import ChangeLoginModalBody from './ChangeLoginModalBody.svelte';
	import { createDialog } from 'svelte-headlessui';
	import ButtonShiftingWidth from '$lib/components/common/ButtonShiftingWidth.svelte';

	const dialog = createDialog({ label: 'authDialog' });
</script>

<ButtonShiftingWidth
	action={dialog.open}
	color="blue"
	text="Change login"
	iconProvider={() => UserPen}
/>

<ModalRender body={() => ChangeLoginModalBody} {dialog} />
