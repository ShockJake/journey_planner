import type Route from "./Route.ts";

export default interface UserDataResult {
    username: string,
    routesCreated: number,
    routes: Route[],
    error: string | null;
}
