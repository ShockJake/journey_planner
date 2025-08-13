<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import Alert from '$lib/components/common/Alert.svelte';
	import { Trash2, CircleAlert } from '$lib/components/common/Icons.ts';
	import type DialogInterface from '$lib/components/common/DialogInterface.ts';
	import { deleteAccount } from '$lib/component_scripts/user.ts';
	import Button from '$lib/components/common/buttons/Button.svelte';
	export let dialog: DialogInterface;

	function confirmAccountDeletion() {
		deleteAccount().then(dialog.close);
	}
</script>

<div class="px-6 transition">
	<div class="container mb-4 flex items-center justify-center">
		<h1 class="text-lg leading-6 font-bold text-gray-900">
			<span class="flex items-center">Delete account</span>
		</h1>
	</div>
	<div class="grid w-full items-center justify-center gap-3">
		<Alert
			message="You can't get back after this!"
			iconSupplier={() => CircleAlert}
			type="danger"
		/>
		<div class="flex w-full items-center justify-center text-center">
			<Button
				text="Delete"
				iconProvider={() => Trash2}
				color="red"
				action={confirmAccountDeletion}
			/>
		</div>
	</div>
</div>
