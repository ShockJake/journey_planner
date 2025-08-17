<script lang="ts">
	import { fade } from 'svelte/transition';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import Alert from '$lib/components/common/Alert.svelte';
	import { UserPen, KeyRound, CircleAlert } from '$lib/components/common/Icons.ts';
	import { changePassword } from '$lib/component_scripts/user.ts';
	import type DialogInterface from '$lib/components/common/DialogInterface.ts';
	import Button from '$lib/components/common/buttons/Button.svelte';
	export let dialog: DialogInterface;

	let newPassword = '';
	let newPasswordConfirm = '';
	let errorMessage = '';

	function confirmPasswordChange() {
		errorMessage = '';
		if (newPassword !== newPasswordConfirm) {
			errorMessage = 'Passwords must match!';
			return;
		}
		changePassword(newPassword).then((error) => {
			if (error === '') {
				dialog.close();
			}
			errorMessage = error;
		});
	}
</script>

<div class="px-6 transition">
	<div class="container mb-4 flex items-center justify-center">
		<h1 class="text-lg leading-6 font-bold text-gray-900">
			<span class="flex items-center">Change password</span>
		</h1>
	</div>
	<div class="grid w-full items-center justify-center gap-3">
		<div class="flex flex-col gap-1">
			<label for="newPassword" class="text-medium block text-[#07074D]">
				<TextWithIcon text="New password" icon={() => KeyRound} />
			</label>
			<input
				type="password"
				name="Password"
				id="newPassword"
				placeholder="New password"
				bind:value={newPassword}
				class="w-70 rounded-md border border-[#e0e0e0] bg-white px-6 py-3 text-sm text-[#6B7280] transition outline-none focus:border-green-500"
			/>
		</div>
		<div class="flex flex-col gap-1">
			<label for="Login" class="text-medium block text-[#07074D]">
				<TextWithIcon text="Confirm password" icon={() => KeyRound} />
			</label>
			<input
				type="password"
				name="ConfirmPassword"
				id="ConfirmPassword"
				placeholder="Confirm password"
				bind:value={newPasswordConfirm}
				class="w-full rounded-md border border-[#e0e0e0] bg-white px-6 py-3 text-sm text-[#6B7280] transition outline-none focus:border-green-500"
			/>
		</div>
		{#if errorMessage.length !== 0}
			<div in:fade>
				<Alert message={errorMessage} iconSupplier={() => CircleAlert} type="danger" />
			</div>
		{/if}
		<Alert message="You will be logged out" iconSupplier={() => CircleAlert} type="warning" />
		<div class="flex w-full items-center justify-center text-center">
			<Button
				text="Change"
				iconProvider={() => UserPen}
				color="amber"
				action={confirmPasswordChange}
			/>
		</div>
	</div>
</div>
