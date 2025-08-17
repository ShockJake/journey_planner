<script lang="ts">
	import { KeyRound } from '$lib/components/common/Icons.ts';
	import ModalRender from '$lib/components/common/ModalRender.svelte';
	import ChangePasswordModalBody from './ChangePasswordModalBody.svelte';
	import { createDialog } from 'svelte-headlessui';
	import ButtonShiftingWidth from '$lib/components/common/buttons/ButtonShiftingWidth.svelte';

	const dialog = createDialog({ label: 'authDialog' });
</script>

<ButtonShiftingWidth
	action={dialog.open}
	color="amber"
	text="Change password"
	iconProvider={() => KeyRound}
/>

<ModalRender body={() => ChangePasswordModalBody} {dialog} />
