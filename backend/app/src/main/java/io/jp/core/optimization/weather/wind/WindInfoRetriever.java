package io.jp.core.optimization.weather.wind;

public interface WindInfoRetriever<WindDataType, ForecastType> {
    WindDataType getWindData(ForecastType forecast);
}
