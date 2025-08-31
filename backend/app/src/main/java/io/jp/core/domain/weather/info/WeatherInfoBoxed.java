package io.jp.core.domain.weather.info;

import io.jp.core.domain.weather.general.GeneralData;
import io.jp.core.domain.weather.general.GeneralDataBoxed;
import io.jp.core.domain.weather.rain.RainDataBoxed;
import io.jp.core.domain.weather.wind.WindDataBoxed;
import lombok.Builder;

import java.util.List;

@Builder
public record WeatherInfoBoxed(GeneralDataBoxed generalData, List<RainDataBoxed> rainData, WindDataBoxed windData) {
}
