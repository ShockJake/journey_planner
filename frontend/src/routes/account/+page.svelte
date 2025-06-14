<script lang="ts">
	import UserAccount from '$lib/components/user/account/UserAccount.svelte';
	import { fade } from 'svelte/transition';
</script>

<svelte:head>
	<title>Account</title>
</svelte:head>

<div in:fade class="relative z-10 flex h-full items-center justify-center text-center">
	<UserAccount />
</div>
