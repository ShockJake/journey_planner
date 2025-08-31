package io.jp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.database.entities.route.PlaceType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MappingUtils {
    public static String mapDataToDescription(String municipality, List<PlaceType> placeTypes) {
        var placesDescription = placeTypes.stream()
                .map(PlaceType::name)
                .map(String::toLowerCase)
                .collect(Collectors.joining(","));
        return "Discover %s by visiting %s.".formatted(municipality, placesDescription);
    }

    public static <T> T readObjectFromString(ObjectMapper mapper, String input, Class<T> clazz) {
        try {
            log.debug("Deserializing object to {}", clazz.getSimpleName());
            return mapper.readValue(input, clazz);
        } catch (JsonProcessingException e) {
            log.error("Cannot map {}", clazz.getSimpleName(), e);
            throw new IllegalArgumentException("Cannot perform map operation from JSON");
        }
    }

    public static String writeObjectToString(ObjectMapper mapper, Object object) {
        try {
            log.debug("Serializing object to {}", object.getClass().getSimpleName());
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Cannot serialize {}", object, e);
            throw new IllegalArgumentException("Cannot perform map operation to JSON");
        }
    }
}