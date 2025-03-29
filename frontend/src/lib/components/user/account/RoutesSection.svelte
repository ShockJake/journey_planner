<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { Route } from '$lib/components/common/Icons.ts';
</script>

<div class="min-h-0 grow rounded-lg bg-white/70">
	<h2
		class="flex w-full items-center justify-center rounded-lg bg-white/80 py-2 text-2xl font-bold text-gray-800 drop-shadow-md"
	>
		<div class="w-auto">
			<TextWithIcon text="Routes" icon={() => Route} />
		</div>
	</h2>
	<div class="flex h-9/10 w-full items-center justify-center p-3 text-center text-xl text-gray-800">
		Nothing to show.
	</div>
</div>
