<script lang="ts">
	import { onMount } from 'svelte';
	import { toggleMenu } from '$lib/component_scripts/navbar.ts';
	import AuthenticationModal from './AuthenticationModal.svelte';
	import {
		MapPinned,
		PhoneCall,
		Info,
		Menu,
		CircleX,
		CircleUserRound
	} from '$lib/components/Icons.ts';

	let isAuthenticated = false;
	let isOpen: boolean = false;

	onMount(() => {
		isAuthenticated = localStorage.getItem('token') == null;
	});
</script>

<nav class="bg-gradient-to-r from-green-500 to-orange-500 p-2 text-white shadow-md transition">
	<div class="container mx-auto flex items-center justify-between">
		<a href="/" class="rounded-lg px-3 py-2 text-2xl font-bold transition hover:bg-white/30">
			<div class="container mx-1 flex items-center justify-between">
				<MapPinned />Journey Planner
			</div>
		</a>

		<div class="hidden space-x-1 md:flex">
			<a href="/info" class="rounded-lg px-3 py-3 transition hover:bg-white/30"><Info /></a>
			<a href="/contact" class="rounded-lg px-3 py-3 transition hover:bg-white/30"><PhoneCall /></a>
			{#if isAuthenticated}
				<AuthenticationModal />
			{:else}
				<a href="/account" class="rounded-lg px-3 py-3 transition hover:bg-white/30"
					><CircleUserRound /></a
				>
			{/if}
		</div>

		<button
			class="rounded-lg px-3 py-3 transition hover:bg-white/30 md:hidden"
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
			<a href="/info" class="rounded-lg px-3 py-3 transition hover:bg-white/30">
				<div class="container mx-1 flex items-center">
					<Info />&nbsp;Info
				</div>
			</a>
			<a href="/contact" class="rounded-lg px-3 py-3 transition hover:bg-white/30">
				<div class="container mx-1 flex items-center">
					<PhoneCall />&nbsp;Contact
				</div>
			</a>
			<a href="/account" class="rounded-lg px-3 py-3 transition hover:bg-white/30">
				<div class="container mx-1 flex items-center">
					<CircleUserRound />&nbsp;Account
				</div>
			</a>
		</div>
	{/if}
</nav>
