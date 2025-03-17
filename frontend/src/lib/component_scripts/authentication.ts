import axios from 'axios';
import { baseUrl } from '$lib/component_scripts/server.ts';

export async function register(username: string, password: string) {
	try {
		const response = await axios.post(`${baseUrl()}/auth/register`, { username, password });
		if (response.status == 200) {
			alert('User registered');
		}
	} catch (error) {
		alert('Registeration failed: ' + error);
	}
}

export async function login(username: string, password: string) {
	try {
		const response = await axios.post(`${baseUrl()}/auth/login`, { username, password });
		if (response.status == 200) {
			alert('User logged');
			console.log(`Token: ${response.data.token}`);
		}
	} catch (error) {
		alert('Login failed: ' + error);
	}
}
