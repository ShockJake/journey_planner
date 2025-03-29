<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import Alert from '$lib/components/common/Alert.svelte';
	import { UserPen, KeyRound, CircleAlert } from '$lib/components/common/Icons.ts';
	import { changePassword } from '$lib/component_scripts/user.ts';
	import type DialogInterface from '$lib/components/common/DialogInterface.ts';
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
			if (error !== '') {
				errorMessage = error;
			}
			dialog.close();
		});
	}
</script>

<div class="transition">
	<div class="container mb-8 flex items-center justify-center">
		<h1 class="text-2xl leading-6 font-bold text-gray-900">
			<span class="flex items-center">Change password</span>
		</h1>
	</div>
	<div class="grid w-full items-center justify-center">
		<label for="newPassword" class="text-medium mb-1 block text-[#07074D]">
			<TextWithIcon text="New password" icon={() => KeyRound} />
		</label>
		<input
			type="password"
			name="Password"
			id="newPassword"
			placeholder="New password"
			bind:value={newPassword}
			class="text-medium w-full rounded-md border border-[#e0e0e0] bg-white px-6 py-3 text-[#6B7280] transition outline-none focus:border-green-500"
		/>
		<label for="Login" class="text-medium mt-3 mb-1 block text-[#07074D]">
			<TextWithIcon text="Confirm password" icon={() => KeyRound} />
		</label>
		<input
			type="password"
			name="ConfirmPassword"
			id="ConfirmPassword"
			placeholder="Confirm password"
			bind:value={newPasswordConfirm}
			class="text-medium w-full rounded-md border border-[#e0e0e0] bg-white px-6 py-3 text-[#6B7280] transition outline-none focus:border-green-500"
		/>

		<Alert message="You will be logged out" iconSupplier={() => CircleAlert} type="warning" />
		<div class="mt-1 text-center">
			<button
				on:click={confirmPasswordChange}
				class="mt-1 inline-flex justify-center rounded-md border border-transparent bg-cyan-100 py-2 pr-2 pl-1 text-lg font-medium text-nowrap text-cyan-900 transition hover:bg-cyan-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-cyan-500 focus-visible:ring-offset-2"
			>
				<TextWithIcon text="Change" icon={() => UserPen} />
			</button>
		</div>
	</div>
</div>
