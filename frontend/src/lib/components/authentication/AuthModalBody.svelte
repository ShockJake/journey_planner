<script lang="ts">
	import { login, register } from '$lib/component_scripts/authentication.ts';
	import { toggleRegistration } from '$lib/component_scripts/authenticationModal.ts';
	import TextWithIcon from '../common/TextWithIcon.svelte';
	import { ShieldAlert, User, KeyRound } from '../common/Icons.ts';
	import Alert from '../common/Alert.svelte';

	let isRegistering = false;
	let username: string = '';
	let password: string = '';
	let confirmPassword: string = '';
	let errorMessage: string = '';

	function loginUser() {
		login(username, password).then((msg) => (errorMessage = msg));
	}

	function registerUser() {
		if (password !== confirmPassword) {
			errorMessage = 'Passwords must match!';
			return;
		}
		register(username, password).then((msg) => {
			errorMessage = msg;
			if (errorMessage === '') {
				isRegistering = !isRegistering;
			}
		});
	}
</script>

<div class="transition">
	<div class="container mb-6 flex items-center justify-center">
		<h1 class="text-2xl leading-6 font-bold text-gray-900">
			{#if isRegistering}
				<span class="flex items-center">Register</span>
			{:else}
				<span class="flex items-center">Login</span>
			{/if}
		</h1>
	</div>
	<div class="grid w-full items-center justify-center">
		<div class="mb-1">
			<label for="Login" class="text-medium mb-1 block text-[#07074D]">
				<TextWithIcon text="Login" icon={() => User} />
			</label>
			<input
				type="text"
				name="Login"
				id="Login"
				placeholder="Login"
				bind:value={username}
				class="text-medium w-full rounded-md border border-[#e0e0e0] bg-white px-6 py-3 text-[#6B7280] transition outline-none focus:border-green-500"
			/>
		</div>
		<div class="mb-2">
			<label for="Password" class="text-medium mt-3 mb-1 block text-[#07074D]">
				<TextWithIcon text="Password" icon={() => KeyRound} />
			</label>
			<input
				type="password"
				name="Password"
				id="Password"
				placeholder="Password"
				bind:value={password}
				class="text-medium w-full rounded-md border border-[#e0e0e0] bg-white px-6 py-3 text-[#6B7280] transition outline-none focus:border-green-500"
			/>
		</div>
		{#if isRegistering}
			<div class="mb-2">
				<label for="ConfirmPassword" class="text-medium mt-3 mb-1 block text-[#07074D]">
					<TextWithIcon text="Confirm Password" icon={() => KeyRound} />
				</label>
				<input
					type="password"
					name="ConfirmPassword"
					id="ConfirmPassword"
					placeholder="Confirm Password"
					bind:value={confirmPassword}
					class="text-medium w-full rounded-md border border-[#e0e0e0] bg-white px-6 py-3 text-[#6B7280] outline-none focus:border-green-500"
				/>
			</div>
		{/if}
		{#if errorMessage.length !== 0}
			<Alert message={errorMessage} iconSupplier={() => ShieldAlert} type="danger" />
		{/if}
		{#if isRegistering}
			<button
				class="text-medium mt-2 inline-flex justify-center rounded-md border border-transparent bg-blue-100 px-4 py-2 font-medium text-blue-900 transition hover:bg-blue-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
				on:click={registerUser}
				>Register
			</button>
			<button
				class="text-medium mt-2 inline-flex justify-center rounded-md border border-transparent bg-amber-100 px-4 py-2 font-medium text-amber-900 transition hover:bg-amber-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-amber-500 focus-visible:ring-offset-2"
				on:click={() => (isRegistering = toggleRegistration(isRegistering))}
				>Back to login
			</button>
		{/if}
		{#if !isRegistering}
			<button
				on:click={loginUser}
				class="text-medium mt-2 inline-flex justify-center rounded-md border border-transparent bg-blue-100 px-4 py-2 font-medium text-blue-900 transition hover:bg-blue-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
				>Login
			</button>
			<button
				class="text-medium mt-2 inline-flex justify-center rounded-md border border-transparent bg-green-100 px-4 py-2 font-medium text-green-900 transition hover:bg-green-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-green-500 focus-visible:ring-offset-2"
				on:click={() => (isRegistering = toggleRegistration(isRegistering))}
				>Go to registration
			</button>
		{/if}
	</div>
</div>
