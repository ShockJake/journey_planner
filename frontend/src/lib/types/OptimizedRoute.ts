import type Route from "./Route.ts";
import type WeatherInfo from "./WeatherInfo.ts";

export default interface OptimizedRoute {
    route: Route,
    weatherInfo: WeatherInfo
}
