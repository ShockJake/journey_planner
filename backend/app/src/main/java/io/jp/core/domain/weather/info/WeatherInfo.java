package io.jp.core.domain.weather.info;

import io.jp.core.domain.weather.general.GeneralData;
import io.jp.core.domain.weather.rain.RainData;
import io.jp.core.domain.weather.wind.WindData;
import lombok.Builder;

import java.util.List;

@Builder
public record WeatherInfo(GeneralData generalData, List<RainData> rainData, WindData windData) {
}
