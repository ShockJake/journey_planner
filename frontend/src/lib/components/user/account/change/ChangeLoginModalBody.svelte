<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { UserPen, User, CircleAlert } from '$lib/components/common/Icons.ts';
	import type DialogInterface from '$lib/components/common/DialogInterface.ts';
	import { changeLogin } from '$lib/component_scripts/user.ts';
	import Alert from '$lib/components/common/Alert.svelte';
	export let dialog: DialogInterface;

	let newUsername = '';
	let errorMessage = '';

	function confirmLoginChange() {
		changeLogin(newUsername).then(() => dialog.close());
	}
</script>

<div class="transition">
	<div class="container mb-8 flex items-center justify-center">
		<h1 class="text-2xl leading-6 font-bold text-gray-900">
			<span class="flex items-center">Change login</span>
		</h1>
	</div>
	<div class="grid w-full items-center justify-center">
		<label for="newLogin" class="text-medium mb-1 block text-[#07074D]">
			<TextWithIcon text="New login" icon={() => User} />
		</label>
		<input
			type="text"
			name="Login"
			id="newLogin"
			placeholder="New login"
			bind:value={newUsername}
			class="text-medium w-full rounded-md border border-[#e0e0e0] bg-white px-6 py-3 text-[#6B7280] transition outline-none focus:border-green-500"
		/>
		<Alert message="You will be logged out" iconSupplier={() => CircleAlert} type="warning" />

		<div class="mt-1 text-center">
			<button
				on:click={confirmLoginChange}
				class="mt-3 inline-flex justify-center rounded-md border border-transparent bg-blue-100 py-2 pr-2 pl-1 text-lg font-medium text-nowrap text-blue-900 transition hover:bg-blue-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
			>
				<TextWithIcon text="Change" icon={() => UserPen} />
			</button>
		</div>
	</div>
</div>
