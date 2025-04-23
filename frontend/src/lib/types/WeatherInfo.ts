import type GeneralWeatherData from "./GeneralWeatherData.ts";
import type RainData from "./RainData.ts";
import type WindData from "./WindData.ts";

export default interface WeatherInfo {
    generalData: GeneralWeatherData,
    rainData: RainData[],
    windData: WindData
}
