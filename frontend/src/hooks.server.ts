import type { Handle } from '@sveltejs/kit';

export const handle: Handle = async ({ event, resolve }) => {
    const url = event.url.href;
    const method = event.request.method;

    const userAgent = event.request.headers.get('user-agent');
    const ip =
        event.getClientAddress?.() ||
        event.request.headers.get('x-forwarded-for'); // fallback

    console.log(`[${new Date().toISOString()}] ${method} ${url}, UA: ${userAgent}, IP: ${ip}`);

    // Continue resolving request
    const response = await resolve(event);

    // You can log response details too
    console.log(`[${new Date().toISOString()}] Responded to ${url} with status: ${response.status}`);

    return response;
};
