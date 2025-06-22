<script lang="ts">
	import Header from './Header.svelte';
	import InfoSection from './InfoSection.svelte';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { CircleAlert } from '$lib/components/common/Icons.ts';
	import RoutesSection from './RoutesSection.svelte';
	import type UserDataResult from '$lib/types/UserDataResult.ts';
	import { onMount } from 'svelte';
	import { getUserData, username } from '$lib/component_scripts/user.ts';
	import type Route from '$lib/types/Route.ts';
	import type OptimizedRoute from '$lib/types/OptimizedRoute.ts';
	import OptimizedRoutesSection from './OptimizedRoutesSection.svelte';

	let routesCreated = 0;
	let errorMsg = '';
	let routes: Route[] = [];
	let optimizedRoutes: OptimizedRoute[] = [];

	onMount(async () => {
		const userdata: UserDataResult = await getUserData();
		if (userdata.error !== null) {
			errorMsg = userdata.error;
			return;
		}
		username.set(userdata.username);
		routesCreated = userdata.routesCreated;
		routes = userdata.routes;
		optimizedRoutes = userdata.optimizedRoutes;
	});
</script>

<div class="flex h-full w-full flex-col">
	<Header />
	<div class="flex min-h-0 w-full grow flex-col">
		{#if errorMsg.length !== 0}
			<div class="flex h-full w-full items-center justify-center">
				<div class="my-4 rounded-md bg-red-600 py-2 pr-2 pl-1 font-medium text-white">
					<TextWithIcon text={errorMsg} icon={() => CircleAlert} />
				</div>
			</div>
		{:else}
			<div class="w-full gap-2 overflow-scroll p-3">
				<InfoSection username={$username} {routesCreated} />
				<div class="flex w-full flex-col space-y-3 px-3 pb-3">
					<RoutesSection {routes} />
					<OptimizedRoutesSection {optimizedRoutes} />
				</div>
			</div>
		{/if}
	</div>
</div>
