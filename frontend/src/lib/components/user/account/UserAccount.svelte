<script lang="ts">
	import Header from './Header.svelte';
	import InfoSection from './InfoSection.svelte';
	import RoutesSection from './RoutesSection.svelte';
</script>

<div class="flex h-9/10 w-9/10 items-center justify-center rounded-lg bg-white/65">
	<div class="flex h-full w-full flex-col">
		<Header />
		<InfoSection />
		<div class="flex min-h-0 w-full grow flex-row space-y-3 px-3 pb-3">
			<RoutesSection />
		</div>
	</div>
</div>
