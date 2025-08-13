<script lang="ts">
	import type { Church } from '../Icons.ts';

	interface Props {
		color: string;
		iconProvider: (() => typeof Church) | undefined;
		text: string;
		action: () => void;
		width: string | undefined;
	}
	const { color, iconProvider, text, action, width }: Props = $props();
</script>
