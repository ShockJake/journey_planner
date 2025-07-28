<script lang="ts">
	import Header from './Header.svelte';
	import Form from './generation_form/Form.svelte';
</script>

<div class="flex h-full w-full flex-col">
	<Header />
	<div class="flex min-h-0 w-full grow flex-row space-y-3 overflow-y-scroll p-2">
		<Form />
	</div>
</div>
