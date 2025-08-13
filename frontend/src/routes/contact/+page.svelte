<script lang="ts">
	import { Mails, PhoneCall, CircleUserRound } from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { fade } from 'svelte/transition';
</script>

<svelte:head>
	<title>Contact</title>
</svelte:head>

<div in:fade class="relative z-10 flex h-full flex-col items-center justify-center text-center">
	<div class="flex h-9/10 w-9/10 flex-col items-center justify-center rounded-lg bg-white/65">
		<div class="flex h-full w-full flex-col">
			<h1
				class="flex w-full flex-row items-center justify-center rounded-lg bg-white py-2 text-3xl font-bold text-gray-800 drop-shadow-md"
			>
				<div class="w-auto">
					<TextWithIcon text="Contact" icon={() => CircleUserRound} />
				</div>
			</h1>
			<div class="flex min-h-0 w-full grow flex-col p-3 sm:flex-col lg:flex-row">
				<div
					class="flex w-full grow flex-col items-center space-y-5 sm:flex-col lg:flex-row lg:space-y-0"
				>
					<div
						class="container mx-1 flex items-center justify-center text-xs text-gray-800 lg:text-3xl"
					>
						<Mails />&nbsp;stanislaw.lysunenko@student.uj.edu.pl
					</div>
					<div
						class="container mx-1 flex items-center justify-center text-xs text-gray-800 lg:text-3xl"
					>
						<PhoneCall />&nbsp;+48123456789
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
