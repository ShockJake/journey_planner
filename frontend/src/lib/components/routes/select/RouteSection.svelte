<script lang="ts">
	import { Route, SquareCheck, CircleEllipsis } from '$lib/components/common/Icons.ts';
	import TextWithIcon from '$lib/components/common/TextWithIcon.svelte';
</script>

<div class="flex w-full flex-col rounded-lg bg-white/70 transition hover:bg-white">
	<div class="rounded-lg p-3">
		<img
			class="h-48 w-full rounded-lg object-cover"
			src="https://upload.wikimedia.org/wikipedia/commons/thumb/e/e2/20091114_Krakow_Wawel_7770.jpg/2880px-20091114_Krakow_Wawel_7770.jpg"
			alt="test-img"
		/>
	</div>
	<div class="mr-3 ml-5">
		<div class="text-3xl">
			<TextWithIcon text="Test Route" icon={() => Route} />
		</div>
		<div class="my-2 border-t border-gray-300"></div>
		<div class="text-lg text-gray-500">
			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nulla magna, commodo sit amet
			maximus et, sollicitudin ut elit. Proin mollis velit a ultricies commodo. Nullam pretium
			molestie mi, ac tempus augue iaculis a. In dictum rutrum urna. Fusce efficitur scelerisque
			gravida. Morbi velit felis, facilisis aliquam faucibus quis, consequat sit amet leo. Nullam
			sit amet lectus lobortis, aliquet neque et, malesuada purus. Morbi imperdiet in felis
			consequat consectetur.
		</div>
		<div class="mt-2 mb-3 flex w-full items-end justify-end gap-3">
			<button
				class="flex justify-center rounded-md border border-transparent bg-blue-100 py-2 pr-2 pl-1 text-lg font-medium text-nowrap text-blue-900 transition hover:bg-blue-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
			>
				<TextWithIcon text="More" icon={() => CircleEllipsis} />
			</button>
			<button
				class="flex justify-center rounded-md border border-transparent bg-green-100 py-2 pr-2 pl-1 text-lg font-medium text-nowrap text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-green-500 focus-visible:ring-offset-2"
			>
				<TextWithIcon text="Quick Select" icon={() => SquareCheck} />
			</button>
		</div>
	</div>
</div>
