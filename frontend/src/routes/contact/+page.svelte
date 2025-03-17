<script lang="ts">
	import { Mails, PhoneCall } from '$lib/components/Icons.ts';
</script>

<svelte:head>
	<title>Contact</title>
</svelte:head>

<div class="relative z-10 flex h-full flex-col items-center justify-center text-center">
	<div class="flex h-9/10 w-9/10 flex-col items-center justify-center rounded-lg bg-white/65">
		<h1 class="mb-6 text-3xl font-bold text-gray-800">Contact</h1>
		<div class="container mt-2 flex w-full items-center justify-center">
			<div class="container mx-1 flex flex-col items-center text-gray-800">
				<Mails size={64} class="mb-2" />&nbsp;stanislaw.lysunenko@student.uj.edu.pl
			</div>
			<div class="container mx-1 flex flex-col items-center text-gray-800">
				<PhoneCall size={64} class="mb-2" />&nbsp;+48123456789
			</div>
		</div>
	</div>
</div>
