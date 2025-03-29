<script lang="ts">
	const { icon, text } = $props();
</script>

<div class="container mx-1 flex items-center">
	{#if icon}
		{@const IconObj = icon()}
		<IconObj />
	{/if}
	&nbsp;{text}
</div>
