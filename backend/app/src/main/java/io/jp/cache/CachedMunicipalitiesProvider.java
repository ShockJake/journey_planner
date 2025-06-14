package io.jp.cache;

import io.jp.database.entities.route.Municipality;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CachedMunicipalitiesProvider {
    private static final Set<Municipality> cachedMunicipalities = new HashSet<>();
    private static final Object LOCK = new Object();

    public static Set<Municipality> getCachedMunicipalities() {
        synchronized (LOCK) {
            return cachedMunicipalities;
        }
    }

    public static Municipality getCachedMunicipality(String name) {
        synchronized (LOCK) {
           return cachedMunicipalities.stream()
                    .filter(item -> item.getName().equals(name))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalStateException("No municipality '%s' found".formatted(name)));
        }
    }

    public static void putCachedMunicipalities(Collection<Municipality> municipalities) {
        synchronized (LOCK) {
            cachedMunicipalities.addAll(municipalities);
        }
    }

    public static void putCachedMunicipality(Municipality municipality) {
        synchronized (LOCK) {
            cachedMunicipalities.add(municipality);
        }
    }

    public static boolean hasNoCachedMunicipalities() {
        synchronized (LOCK) {
            return cachedMunicipalities.isEmpty();
        }
    }
}
