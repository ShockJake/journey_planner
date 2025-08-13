<script lang="ts">
	import UserAccount from '$lib/components/user/account/UserAccount.svelte';
	import { fade } from 'svelte/transition';
</script>

<svelte:head>
	<title>Account</title>
</svelte:head>

<div in:fade class="relative z-10 m-3 flex h-full flex-col items-center">
	<div class="flex h-9/10 w-29/30 items-center justify-center rounded-lg bg-white/65 lg:w-9/10">
		<UserAccount />
	</div>
</div>
