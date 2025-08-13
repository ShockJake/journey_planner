<script>
	export let error;
</script>

<div class="relative h-screen w-full overflow-hidden">
	<div
		class="absolute inset-0 bg-cover bg-center bg-no-repeat"
		style="background-image: url('/images/background.webp');"
	></div>
	<div class="absolute inset-0 bg-white/1 backdrop-blur-sm"></div>
	<div
		class="relative z-10 flex h-full flex-col items-center justify-center text-center text-black"
	>
		<div
			class=" flex h-1/2 w-9/10 flex-col items-center justify-center rounded-lg bg-white/75 lg:w-1/2"
		>
			<h1 class="text-xl font-bold">Oops! Something went wrong.</h1>
			<p class="mt-2 mb-4 text-sm text-gray-500">{error?.message || 'Unknown error occurred.'}</p>
			<a
				href="/"
				class="flex justify-center rounded-md border border-transparent bg-blue-100 px-5 py-2 text-xs font-medium text-nowrap text-blue-900 transition hover:bg-blue-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2 lg:text-sm"
			>
				Go Home
			</a>
		</div>
	</div>
</div>
