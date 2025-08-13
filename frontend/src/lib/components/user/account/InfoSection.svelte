<script lang="ts">
	import ChangeLoginModal from './change/ChangeLoginModal.svelte';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { Info, User, Route, CalendarDays } from '$lib/components/common/Icons.ts';
	import ChangePasswordModal from './change/ChangePasswordModal.svelte';
	import DeleteUserModal from './delete/DeleteUserModal.svelte';

	interface Props {
		username: string;
		savedRoutes: number;
		optimizedRoutes: number;
		createdAt: string;
	}

	const { username, savedRoutes, optimizedRoutes, createdAt }: Props = $props();
</script>

<div class="w-full p-3">
	<div class="rounded-lg bg-white/70">
		<h2
			class="flex w-full items-center justify-center rounded-lg bg-white/90 py-2 text-lg font-bold text-gray-800 drop-shadow-sm lg:text-2xl"
		>
			<div class="w-auto">
				<TextWithIcon text="Info" icon={() => Info} />
			</div>
		</h2>
		<div
			class="flex w-full flex-col items-center justify-center gap-2 p-3 text-gray-800 lg:flex-row"
		>
			<div
				class="w-full rounded-lg bg-white/90 py-2 pr-2 pl-1 text-sm font-medium shadow-sm lg:w-auto lg:text-base"
			>
				<TextWithIcon text="Login: {username}" icon={() => User} />
			</div>
			<div
				class="w-full rounded-lg bg-white/90 py-2 pr-2 pl-1 text-sm font-medium shadow-sm lg:w-auto lg:text-base"
			>
				<TextWithIcon text="Saved routes: {savedRoutes}" icon={() => Route} />
			</div>
			<div
				class="w-full rounded-lg bg-white/90 py-2 pr-2 pl-1 text-sm font-medium shadow-sm lg:w-auto lg:text-base"
			>
				<TextWithIcon text="Routes optimized: {optimizedRoutes}" icon={() => Route} />
			</div>
			<div
				class="w-full rounded-lg bg-white/90 py-2 pr-2 pl-1 text-sm font-medium shadow-sm lg:w-auto lg:text-base"
			>
				<TextWithIcon text="Created: {createdAt}" icon={() => CalendarDays} />
			</div>
		</div>
		<div class="flex flex-col justify-items-center gap-2 px-3 pb-2 text-gray-800 lg:flex-row">
			<div class="w-full items-center justify-items-center lg:w-auto"><ChangeLoginModal /></div>
			<div class="w-full items-center justify-items-center lg:w-auto">
				<ChangePasswordModal />
			</div>
			<div class="w-full items-center justify-items-center lg:w-auto"><DeleteUserModal /></div>
		</div>
	</div>
</div>
