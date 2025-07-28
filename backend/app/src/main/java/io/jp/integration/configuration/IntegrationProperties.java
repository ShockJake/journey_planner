package io.jp.integration.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@ConfigurationProperties(prefix = "integrations.api")
@RequiredArgsConstructor
public class IntegrationProperties {
    private final Routing routing;
    private final Places places;
    private final Weather weather;


    public record Routing(boolean enabled) {
    }

    public record Places(boolean enabled) {
    }

    public record Weather(boolean enabled) {
    }
}
