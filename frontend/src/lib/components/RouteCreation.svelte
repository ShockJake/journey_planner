<script lang="ts">
	import { createDialog } from 'svelte-headlessui';
	import Transition from 'svelte-transition';
	import { ListTodo, MapPlus, Route } from '$lib/components/common/Icons.ts';

	const dialog = createDialog({ label: 'routeCreationDialog' });
</script>

<button
	type="button"
	class="rounded-md bg-green-100 px-4 py-2 text-lg font-medium text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-white/75"
	onclick={dialog.open}>Start</button
>
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
								<span class="container mx-2 flex items-center">
									Create a Route&nbsp;
									<Route />
								</span>
							</h3>
						</div>
						<div class="my-4 border-t border-gray-300"></div>
						<div class="flex space-x-6">
							<div class="w-1/2">
								<h4 class="text-md font-medium text-gray-800">
									<span class="container flex items-center"><ListTodo />&nbsp;Predefined Route</span
									>
								</h4>
								<p class="text-sm text-gray-500">
									Select from the list of predefined routes and see the most popular places!
								</p>
								<a
									href="/route/select"
									class="mt-2 inline-flex justify-center rounded-md border border-transparent bg-blue-100 px-4 py-2 text-sm font-medium text-blue-900 transition hover:bg-blue-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
									>Select
								</a>
							</div>
							<div class="h-auto border-l border-gray-300"></div>
							<div class="w-1/2">
								<h4 class="text-md font-medium text-gray-800">
									<span class="container flex items-center"><MapPlus />&nbsp;Generated Route</span>
								</h4>
								<p class="text-sm text-gray-500">
									Generate a Route based on your preferences! Specify types of places, your pace and
									more!
								</p>
								<a
									href="/route/create"
									class="mt-2 inline-flex justify-center rounded-md border border-transparent bg-green-100 px-4 py-2 text-sm font-medium text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
									>Generate
								</a>
							</div>
						</div>
					</div>
				</Transition>
			</div>
		</div>
	</Transition>
</div>
