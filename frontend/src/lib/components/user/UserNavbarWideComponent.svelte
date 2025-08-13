<script lang="ts">
	import { goto } from '$app/navigation';
	import { isAuthenticated } from '$lib/component_scripts/authentication.ts';
	import AuthModal from '../authentication/AuthModal.svelte';
	import { CircleUserRound, LogOut } from '../common/Icons.ts';

	import { logout } from '$lib/component_scripts/authentication.ts';
	function goToAccount() {
		goto('/account');
	}
</script>

{#if $isAuthenticated}
	<button class="rounded-lg px-2 py-2 transition hover:bg-white/30" onclick={goToAccount}>
		<div class="container mx-1 flex items-center">
			<CircleUserRound />&nbsp;Account
		</div>
	</button>
	<button class="rounded-lg px-2 py-2 transition hover:bg-white/30" onclick={logout}>
		<div class="container mx-1 flex items-center">
			<LogOut />&nbsp;Log out
		</div>
	</button>
{:else}
	<AuthModal isWide={true} />
{/if}
