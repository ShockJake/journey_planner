<script lang="ts">
	import { goto } from '$app/navigation';
	import { CircleUserRound, LogOut } from '../common/Icons.ts';
	import { createMenu } from 'svelte-headlessui';
	import Transition from 'svelte-transition';
	import { logout } from '$lib/component_scripts/authentication.ts';

	const menu = createMenu({ label: 'Actions' });

	function goToAccount() {
		goto('/account');
	}

	const options = [
		{ icon: CircleUserRound, text: 'Account', color: 'text-black', action: goToAccount },
		{ icon: LogOut, text: 'Log out', color: 'text-red-900', action: logout }
	];
</script>

<div class="relative inline-block text-left">
	<button class="rounded-lg px-2 py-3 transition hover:bg-white/30" use:menu.button>
		<div class="container mx-1 flex items-center">
			<CircleUserRound />
		</div>
	</button>
	<Transition
		show={$menu.expanded}
		enter="transition ease-out duration-200"
		enterFrom="transform opacity-0 scale-95"
		enterTo="transform opacity-100 scale-100"
		leave="transition ease-in duration-175"
		leaveFrom="transform opacity-100 scale-100"
		leaveTo="transform opacity-0 scale-95"
	>
		<div
			use:menu.items
			class="absolute right-0 z-20 mt-0.5 origin-top-right rounded-md bg-white shadow-2xl ring-1 ring-black/5 focus:outline-hidden"
		>
			{#each options as option}
				{@const action = option.action}
				{@const active = $menu.active === option.text}
				{@const color = option.color}
				<button
					on:click={action}
					use:menu.item
					class="group flex w-full items-center {color} rounded-md px-4 py-4 text-sm font-medium transition {active
						? 'bg-gray-100'
						: 'text-gray-900'}"
				>
					<option.icon class="mr-2 h-5 w-5" {active} />
					{option.text}
				</button>
			{/each}
		</div>
	</Transition>
</div>
