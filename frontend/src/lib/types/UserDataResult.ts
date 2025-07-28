import type OptimizedRoute from "./OptimizedRoute.ts";
import type Route from "./Route.ts";

export default interface UserDataResult {
    username: string,
    createdAt: string,
    routes: Route[],
    optimizedRoutes: OptimizedRoute[],
    error: string | null;
}
