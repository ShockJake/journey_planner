<script lang="ts">
	import TextWithIcon from './TextWithIcon.svelte';
	export let message: string;
	export let iconSupplier;
	export let type: string;

	function resolveAlertType() {
		if (type === 'danger') {
			return 'bg-red-600';
		}
		if (type === 'warning') {
			return 'bg-yellow-500';
		}
		if (type === 'success') {
			return 'bg-blue-600';
		}
	}
</script>

<div
	class=" flex rounded-md {resolveAlertType()} items-center justify-center px-2 py-2 text-sm font-medium text-white"
>
	<TextWithIcon text={message} icon={iconSupplier} />
</div>
