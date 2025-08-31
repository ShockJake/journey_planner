package io.jp.mapper.place;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.core.domain.place.PlaceBoxed;
import io.jp.core.domain.point.PointBoxed;
import io.jp.database.entities.route.PlaceType;
import io.jp.integration.response.PlacesResponseBoxed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.StreamSupport.stream;

@Slf4j
@Component
public class PlacesResponseMapperBoxed {
    private final ObjectMapper mapper = new ObjectMapper();

    public PlacesResponseBoxed map(String input) {
        try {
            JsonNode root = mapper.readTree(input).get("features");
            if (!root.isArray()) {
                throw new RuntimeException("Wrong data supplied");
            }
            var result = stream(root.spliterator(), false)
                    .map(this::mapToPlace)
                    .collect(toCollection(ArrayList::new));
            return PlacesResponseBoxed.builder()
                    .places(result)
                    .build();
        } catch (JsonProcessingException e) {
            return PlacesResponseBoxed.builder().build();
        }
    }

    private PlaceBoxed mapToPlace(JsonNode node) {
        JsonNode properties = node.get("properties");
        var name = properties.get("name").asText();
        var longitude = properties.get("lon").asDouble();
        var latitude = properties.get("lat").asDouble();
        var position = PointBoxed.of(latitude, longitude);
        String type = mapPlaceType(properties, name);
        return PlaceBoxed.builder()
                .name(name)
                .position(position)
                .placeType(PlaceType.valueOf(type))
                .build();
    }

    private String mapPlaceType(JsonNode node, String name) {
        var placeTypes = Stream.of(PlaceType.values())
                .map(PlaceType::name)
                .toList();        var categories = stream(node.get("categories").spliterator(), false)
                .map(this::mapCategory)
                .toList();
        var type = categories.stream()
                .filter(placeTypes::contains)
                .findAny();
        if (type.isPresent()) {
            return type.get();
        }
        try {
            return node.get("historic").get("type").asText().toUpperCase();
        } catch (NullPointerException e) {
            log.error("Cannot find type for {}, using default value", name);
            return "DEFAULT";
        }
    }

    private String mapCategory(JsonNode node) {
        return List.of(node.asText().split("\\.")).getLast().toUpperCase();
    }
}
