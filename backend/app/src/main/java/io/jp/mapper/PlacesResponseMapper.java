package io.jp.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.PlaceType;
import io.jp.rest.response.PlacesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import static java.util.stream.StreamSupport.stream;

@Slf4j
@Component
public class PlacesResponseMapper {
    private final ObjectMapper mapper = new ObjectMapper();

    public PlacesResponse map(String input) {
        try {
            JsonNode root = mapper.readTree(input).get("features");
            if (!root.isArray()) {
                throw new RuntimeException("Wrong data supplied");
            }
            var result = stream(root.spliterator(), false)
                    .map(this::mapToPlace)
                    .toList();
            log.info(result.toString());
            return PlacesResponse.builder()
                    .places(result)
                    .build();
        } catch (JsonProcessingException e) {
            return PlacesResponse.builder().build();
        }
    }

    private PlaceJpa mapToPlace(JsonNode node) {
        JsonNode properties = node.get("properties");
        var name = properties.get("name").asText();
        var longitude = properties.get("lon").asDouble();
        var latitude = properties.get("lat").asDouble();
        var firstAddressLine = properties.get("address_line1").asText();
        var secondAddressLine = properties.get("address_line2").asText();
        var type = "";
        try {
            type = properties.get("historic").get("type").asText();
        } catch (NullPointerException e) {
            log.error("Cannot find type for {}", name);
        }
        return PlaceJpa.builder()
                .name(name)
                .longitude(longitude)
                .latitude(latitude)
//                .firstAddressLine(firstAddressLine)
//                .secondAddressLine(secondAddressLine)
                .type(PlaceType.valueOf(type))
                .build();
    }
}
