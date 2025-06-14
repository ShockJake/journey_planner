<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { UserPen, User, CircleAlert } from '$lib/components/common/Icons.ts';
	import type DialogInterface from '$lib/components/common/DialogInterface.ts';
	import { changeLogin } from '$lib/component_scripts/user.ts';
	import Alert from '$lib/components/common/Alert.svelte';
	import { fade } from 'svelte/transition';
	export let dialog: DialogInterface;

	let newUsername = '';
	let errorMessage = '';

	function confirmLoginChange() {
		changeLogin(newUsername).then((error) => {
			if (error.length === 0) {
				dialog.close();
			}
			errorMessage = error;
		});
	}
</script>

<div class="transition">
	<div class="container mb-4 flex items-center justify-center">
		<h1 class="text-lg leading-6 font-bold text-gray-900">
			<span class="flex items-center">Change login</span>
		</h1>
	</div>
	<div class="grid w-full items-center justify-center gap-3">
		<div class="flex flex-col gap-1">
			<label for="newLogin" class="text-medium block text-[#07074D]">
				<TextWithIcon text="New login" icon={() => User} />
			</label>
			<input
				type="text"
				name="Login"
				id="newLogin"
				placeholder="New login"
				bind:value={newUsername}
				class="w-full rounded-md border border-[#e0e0e0] bg-white px-6 py-3 text-sm font-medium text-[#6B7280] transition outline-none focus:border-green-500"
			/>
		</div>

		{#if errorMessage.length !== 0}
			<div in:fade>
				<Alert message={errorMessage} iconSupplier={() => CircleAlert} type="danger" />
			</div>
		{/if}
		<Alert message="You will be logged out" iconSupplier={() => CircleAlert} type="warning" />

		<div class="text-center">
			<button
				on:click={confirmLoginChange}
				class=" inline-flex justify-center rounded-md border border-transparent bg-blue-100 py-2 pr-2 pl-1 text-sm font-medium text-nowrap text-blue-900 transition hover:bg-blue-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
			>
				<TextWithIcon text="Change" icon={() => UserPen} />
			</button>
		</div>
	</div>
</div>
