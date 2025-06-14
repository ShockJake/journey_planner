<script lang="ts">
	import { type Component } from 'svelte';
	import Transition from 'svelte-transition';
	const { body, dialog } = $props();

	interface ModalBody extends Component {
		dialog: any;
	}
</script>

{#snippet modalBody(body: () => ModalBody, dialog: any)}
	{@const ModalBody = body()}
	<ModalBody {dialog} />
{/snippet}

<div class="relative z-20">
	<Transition show={$dialog.expanded}>
		<Transition
			enter="ease-out duration-300"
			enterFrom="opacity-0"
			enterTo="opacity-100"
			leave="ease-in duration-200"
			leaveFrom="opacity-100"
			leaveTo="opacity-0"
		>
			<button class="fixed inset-0 bg-black/25" aria-label="close" onclick={dialog.close}></button>
		</Transition>

		<div class="fixed inset-0 overflow-y-auto">
			<div class="flex min-h-full items-center justify-center p-4 text-center">
				<Transition
					enter="ease-out duration-300"
					enterFrom="opacity-0 scale-95"
					enterTo="opacity-100 scale-100"
					leave="ease-in duration-200"
					leaveFrom="opacity-100 scale-100"
					leaveTo="opacity-0 scale-95"
				>
					<div
						class="w-full max-w-80 min-w-64 transform overflow-hidden rounded-2xl bg-white py-6 text-left align-middle shadow-xl transition-all"
						use:dialog.modal
					>
						{@render modalBody(body, dialog)}
					</div>
				</Transition>
			</div>
		</div>
	</Transition>
</div>
