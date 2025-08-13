<script lang="ts">
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
	import { CircleUserRound } from '$lib/components/common/Icons.ts';
</script>

<h1
	class="flex w-full flex-row items-center justify-center rounded-lg bg-white py-2 text-xl font-bold text-gray-800 drop-shadow-md lg:text-3xl"
>
	<div class="w-auto">
		<TextWithIcon text="User Account" icon={() => CircleUserRound} />
	</div>
</h1>
