<script lang="ts">
	import { CircleUserRound } from '$lib/components/Icons.ts';
	import { createDialog } from 'svelte-headlessui';
	import Transition from 'svelte-transition';

	const dialog = createDialog({ label: 'authDialog' });
</script>

<button class="rounded-lg px-3 py-3 transition hover:bg-white/30" onclick={dialog.open}>
	<div class="container mx-1 flex items-center">
		<CircleUserRound />
	</div>
</button>

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
						class="w-full max-w-lg transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all"
						use:dialog.modal
					>
						<div class="container mx-2 flex items-center justify-center">
							<h3 class="text-lg leading-6 font-medium text-gray-900">
								<span class="container mx-2 flex items-center">Login</span>
							</h3>
						</div>
					</div>
				</Transition>
			</div>
		</div>
	</Transition>
</div>
