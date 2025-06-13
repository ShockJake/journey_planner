package io.jp.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.core.domain.Path;
import io.jp.core.domain.Point;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.StreamSupport;


import static java.util.stream.StreamSupport.stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class PathMapper {
    private final ObjectMapper objectMapper;

    public Path map(String data) {
        try {
            var json = objectMapper.readTree(data);
            var rootNode = json.get("features").get(0);

            var properties = rootNode.get("properties");
            var geometry = rootNode.get("geometry");

            var distance = properties.get("distance").asDouble();
            var time = properties.get("time").asDouble();
            var points = mapPoints(geometry);

            return Path.builder()
                    .distance(distance)
                    .time(time)
                    .points(points)
                    .build();
        } catch (JsonProcessingException | NullPointerException e) {
            log.error("Failed to map Path", e);
            log.error("JSON data: {}", data);
            return Path.builder().build();
        }
    }

    private List<Point> mapPoints(JsonNode geometry) {
        var coordinates = geometry.get("coordinates");

        return stream(coordinates.spliterator(), false)
                .flatMap(node -> StreamSupport.stream(node.spliterator(), false))
                .map(this::mapPoint)
                .distinct()
                .toList();
    }

    private Point mapPoint(JsonNode data) {
        return Point.of(data.get(1).asDouble(), data.get(0).asDouble());
    }
}
