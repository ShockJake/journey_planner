import type Path from './Path.ts';
import type Route from './Route.ts';
import type WeatherInfo from './WeatherInfo.ts';

export default interface OptimizedRoute {
	route: Route;
	path: Path;
	weatherInfo: WeatherInfo;
}
