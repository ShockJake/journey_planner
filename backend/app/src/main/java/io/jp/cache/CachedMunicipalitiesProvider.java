package io.jp.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.jp.database.entities.route.Municipality;
import io.jp.database.repositories.place.MunicipalityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CachedMunicipalitiesProvider {
    private static final Cache<String, Municipality> CACHED_MUNICIPALITIES = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofDays(365))
            .build();
    private final MunicipalityRepository municipalityRepository;

    public Collection<Municipality> getCachedMunicipalities() {
        return CACHED_MUNICIPALITIES.asMap().values();
    }

    public Municipality getCachedMunicipality(String name) {
        return CACHED_MUNICIPALITIES.get(name, (key) ->
                municipalityRepository.findByName(key)
                        .orElseThrow(() -> new IllegalStateException("No municipality '%s' found".formatted(name))));
    }

    public void putCachedMunicipalities(Collection<Municipality> municipalities) {
        municipalities.forEach(municipality ->
                CACHED_MUNICIPALITIES.put(municipality.getName(), municipality));
    }
}
