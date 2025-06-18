import type OptimizedRoute from "./OptimizedRoute.ts";
import type Route from "./Route.ts";

export default interface UserDataResult {
    username: string,
    routesCreated: number,
    routes: Route[],
    optimizedRoutes: OptimizedRoute[],
    error: string | null;
}
