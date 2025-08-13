<script lang="ts">
	import { toggleMenu } from '$lib/component_scripts/navbar.ts';
	import { MapPinned, PhoneCall, Info, Menu, CircleX } from '$lib/components/common/Icons.ts';
	import UserNavbarComponent from './user/UserNavbarComponent.svelte';
	import UserNavbarWideComponent from './user/UserNavbarWideComponent.svelte';

	let isOpen: boolean = false;
</script>

<nav class="bg-gradient-to-r from-green-500 to-orange-500 p-2 text-white shadow-md transition">
	<div class="container mx-auto flex items-center justify-between px-2">
		<a href="/" class="rounded-lg px-3 py-2 text-2xl font-bold transition hover:bg-white/30">
			<div class="container mr-1 flex items-center justify-between">
				<MapPinned />Journey Planner
			</div>
		</a>

		<div class="hidden md:flex">
			<UserNavbarComponent />
			<a href="/contact" class="cursor-pointer rounded-lg px-3 py-3 transition hover:bg-white/30"
				><PhoneCall /></a
			>
			<a href="/info" class="cursor-pointer rounded-lg px-3 py-3 transition hover:bg-white/30"
				><Info /></a
			>
		</div>

		<button
			class="cursor-pointer rounded-lg px-3 py-3 transition hover:bg-white/30 md:hidden"
			on:click={() => (isOpen = toggleMenu(isOpen))}
		>
			{#if isOpen}
				<CircleX />
			{:else}
				<Menu />
			{/if}
		</button>
	</div>

	{#if isOpen}
		<div class="animate-slide-in mt-4 flex flex-col space-y-2 p-4 md:hidden">
			<a href="/info" class="cursor-pointer rounded-lg px-2 py-2 transition hover:bg-white/30">
				<div class="container mx-1 flex items-center">
					<Info />&nbsp;Info
				</div>
			</a>
			<a href="/contact" class="cursor-pointer rounded-lg px-2 py-2 transition hover:bg-white/30">
				<div class="container mx-1 flex items-center">
					<PhoneCall />&nbsp;Contact
				</div>
			</a>
			<UserNavbarWideComponent />
		</div>
	{/if}
</nav>
