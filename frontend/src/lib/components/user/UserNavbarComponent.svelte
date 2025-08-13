<script lang="ts">
	import { isAuthenticated } from '$lib/component_scripts/authentication.ts';
	import AuthModal from '../authentication/AuthModal.svelte';
	import UserNavbarMenu from './UserNavbarMenu.svelte';
</script>

{#if $isAuthenticated}
	<UserNavbarMenu />
{:else}
	<AuthModal isWide={false} />
{/if}
