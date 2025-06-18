import type Path from './Path.ts';
import type Route from './Route.ts';
import type WeatherInfo from './WeatherInfo.ts';

export default interface OptimizedRoute {
    optimizationId: string
    route: Route;
    path: Path;
    weatherInfo: WeatherInfo;
}
