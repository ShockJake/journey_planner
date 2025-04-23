package io.jp.core.optimizer.weather

import io.jp.core.domain.weather.RainData
import spock.lang.Specification
import spock.lang.Unroll

import static io.jp.core.domain.weather.RainIntensity.LIGHT
import static io.jp.core.domain.weather.RainIntensity.MODERATE

class RainInfoProviderTest extends Specification {
    RainInfoProvider rainInfoProvider = new RainInfoProvider()

    @Unroll
    def 'should calculate rain data correctly'(List<BigDecimal> inputData) {
        given:
        List<Double> data = inputData.collect { it as Double }

        when:
        List<RainData> result = rainInfoProvider.getRainStartEnd(data)

        then:
        result.size() == 2

        result.first.startHour == 1
        result.first.endHour == 2
        result.first.maxAmount == 5.0 as Double
        result.first.maxHour == 1
        result.first.rainIntensity == MODERATE.name()

        result.last.startHour == 5
        result.last.endHour == 6
        result.last.maxAmount == 1.5 as Double
        result.last.maxHour == 5
        result.last.rainIntensity == LIGHT.name()

        where:
        inputData << [[0.0, 5.0, 5.0, 0.0, 0.0, 1.5, 1.5], [0.0, 5.0, 5.0, 0.0, 0.0, 1.5, 1.5, 0.0]]
    }
}
