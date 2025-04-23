<script lang="ts">
	import ChangeLoginModal from './change/ChangeLoginModal.svelte';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { Info, User, Route } from '$lib/components/common/Icons.ts';
	import ChangePasswordModal from './change/ChangePasswordModal.svelte';
	import DeleteUserModal from './delete/DeleteUserModal.svelte';

	interface Props {
		username: string;
		routesCreated: number;
	}

	const { username, routesCreated }: Props = $props();
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
		<div class="flex w-full items-center justify-center p-3 text-gray-800">
			<div class="text-md w-auto rounded-lg bg-white/90 py-2 pr-2 pl-1 font-medium shadow-2xs">
				<TextWithIcon text="Login: {username}" icon={() => User} />
			</div>
			<div class="text-md ml-1 w-auto rounded-lg bg-white/90 py-2 pr-2 pl-1 font-medium shadow-2xs">
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
	</div>
</div>
