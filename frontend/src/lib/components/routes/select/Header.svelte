<script lang="ts">
	import TestWithTwoIcons from '$lib/components/common/TestWithTwoIcons.svelte';
	import { ListTodo, Route } from '$lib/components/common/Icons.ts';
</script>

<h1
	class="flex w-full flex-row items-center justify-center rounded-lg bg-white py-2 text-xl font-bold text-gray-800 drop-shadow-md lg:text-3xl"
>
	<div class="w-auto">
		<TestWithTwoIcons text="Select Route" firstIcon={() => ListTodo} secondIcon={() => Route} />
	</div>
</h1>
