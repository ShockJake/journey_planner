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
		<div class=" flex h-1/2 w-1/2 flex-col items-center justify-center rounded-lg bg-white/65">
			<h1 class="text-4xl font-bold">Oops! Something went wrong.</h1>
			<p class="mt-4 text-lg">{error?.message || 'Unknown error occurred.'}</p>
			<a
				href="/"
				class="my-2 rounded-lg bg-white px-3 py-2 text-black shadow-md transition hover:bg-green-500 hover:text-white"
			>
				Go Home
			</a>
		</div>
	</div>
</div>
