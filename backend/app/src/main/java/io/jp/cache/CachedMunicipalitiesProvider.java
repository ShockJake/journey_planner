package io.jp.cache;

import io.jp.database.entities.route.Municipality;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CachedMunicipalitiesProvider {
    private static final Map<String, Municipality> cachedMunicipalities = new HashMap<>();
    private static final Object LOCK = new Object();

    public static Collection<Municipality> getCachedMunicipalities() {
        synchronized (LOCK) {
            return cachedMunicipalities.values();
        }
    }

    public static Municipality getCachedMunicipality(String name) {
        synchronized (LOCK) {
            if (!cachedMunicipalities.containsKey(name)) {
                log.error("No cached municipality found with name {}, all municipalities {}", name, cachedMunicipalities.keySet());
                throw new IllegalStateException("No municipality '%s' found".formatted(name));
            }
            return cachedMunicipalities.get(name);
        }
    }

    public static void putCachedMunicipalities(Collection<Municipality> municipalities) {
        synchronized (LOCK) {
            municipalities.forEach(municipality ->
                    cachedMunicipalities.put(municipality.getName(), municipality));
            log.info("Saved municipalities: {}", cachedMunicipalities.keySet());
        }
    }

    public static void putCachedMunicipality(Municipality municipality) {
        synchronized (LOCK) {
            cachedMunicipalities.put(municipality.getName(), municipality);
        }
    }

    public static boolean hasNoCachedMunicipalities() {
        synchronized (LOCK) {
            return cachedMunicipalities.isEmpty();
        }
    }
}
