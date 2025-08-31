package io.jp.services.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@ConfigurationProperties(prefix = "service.implementation")
@RequiredArgsConstructor
public class ServicesProperties {
    private final Route route;

    public record Route(String optimization, String generation) {}
}
