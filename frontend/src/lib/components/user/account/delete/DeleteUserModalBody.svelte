<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import Alert from '$lib/components/common/Alert.svelte';
	import { Trash2, CircleAlert } from '$lib/components/common/Icons.ts';
	import type DialogInterface from '$lib/components/common/DialogInterface.ts';
	import { deleteAccount } from '$lib/component_scripts/user.ts';
	export let dialog: DialogInterface;

	function confirmAccountDeletion() {
		deleteAccount().then(dialog.close);
	}
</script>

<div class="transition">
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
		<div class=" text-center">
			<button
				on:click={confirmAccountDeletion}
				class=" inline-flex justify-center rounded-md border border-transparent bg-red-100 py-2 pr-2 pl-1 text-sm font-medium text-nowrap text-red-900 transition hover:bg-red-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-red-500 focus-visible:ring-offset-2"
			>
				<TextWithIcon text="Delete" icon={() => Trash2} />
			</button>
		</div>
	</div>
</div>
