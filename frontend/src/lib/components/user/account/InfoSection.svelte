<script lang="ts">
	import ChangeLoginModal from './change/ChangeLoginModal.svelte';
	import { type UserDataResult, getUserData, username } from '$lib/component_scripts/user.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { Info, User, Route, CircleAlert } from '$lib/components/common/Icons.ts';
	import ChangePasswordModal from './change/ChangePasswordModal.svelte';
	import DeleteUserModal from './delete/DeleteUserModal.svelte';
	import { onMount } from 'svelte';

	let routesCreated = 0;
	let errorMsg = '';

	onMount(async () => {
		const userdata: UserDataResult = await getUserData();
		if (userdata.error !== null) {
			errorMsg = userdata.error;
			return;
		}
		username.set(userdata.username);
		routesCreated = userdata.routesCreated;
	});
</script>

<div class="w-full p-3">
	<div class="rounded-lg bg-white/70">
		<h2
			class="flex w-full items-center justify-center rounded-lg bg-white/90 py-2 text-2xl font-bold text-gray-800 drop-shadow-sm"
		>
			<div class="w-auto">
				<TextWithIcon text="Info" icon={() => Info} />
			</div>
		</h2>
		{#if errorMsg.length === 0}
			<div class="flex w-full items-center justify-center p-3 text-xl text-gray-800">
				<div class="w-auto rounded-lg bg-white/90 py-2 pr-2 pl-1 shadow-2xs">
					<TextWithIcon text="Login: {$username}" icon={() => User} />
				</div>
				<div class="ml-1 w-auto rounded-lg bg-white/90 py-2 pr-2 pl-1 shadow-2xs">
					<TextWithIcon text="Routes: {routesCreated}" icon={() => Route} />
				</div>
			</div>
			<div class="flex flex-col justify-items-center gap-2 p-2 text-gray-800 lg:flex-row">
				<div class="w-full items-center justify-items-center lg:w-auto"><ChangeLoginModal /></div>
				<div class="w-full items-center justify-items-center lg:w-auto">
					<ChangePasswordModal />
				</div>
				<div class="w-full items-center justify-items-center lg:w-auto"><DeleteUserModal /></div>
			</div>
		{:else}
			<div class="flex w-full items-center justify-center">
				<div class="my-4 rounded-md bg-red-600 py-2 pr-2 pl-1 font-medium text-white">
					<TextWithIcon text={errorMsg} icon={() => CircleAlert} />
				</div>
			</div>
		{/if}
	</div>
</div>
