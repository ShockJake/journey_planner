<script lang="ts">
	const { firstIcon, secondIcon, text } = $props();
</script>

<div class="container mx-1 flex items-center">
	{#if firstIcon}
		{@const IconObj = firstIcon()}
		<IconObj />
	{/if}
	&nbsp;{text}&nbsp;
	{#if secondIcon}
		{@const IconObj = secondIcon()}
		<IconObj />
	{/if}
</div>
