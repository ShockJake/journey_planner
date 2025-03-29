<script lang="ts">
	import TestWithTwoIcons from '$lib/components/common/TestWithTwoIcons.svelte';
	import { MapPlus, Route } from '$lib/components/common/Icons.ts';
</script>

<h1
	class="flex w-full flex-row items-center justify-center rounded-lg bg-white py-2 text-3xl font-bold text-gray-800 drop-shadow-md"
>
	<div class="w-auto">
		<TestWithTwoIcons text="Generate Route" firstIcon={() => MapPlus} secondIcon={() => Route} />
	</div>
</h1>
