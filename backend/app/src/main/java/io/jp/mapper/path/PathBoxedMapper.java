package io.jp.mapper.path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.core.domain.path.PathBoxed;
import io.jp.core.domain.point.PointBoxed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.StreamSupport;


import static java.util.stream.StreamSupport.stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class PathBoxedMapper {
    private final ObjectMapper objectMapper;

    public PathBoxed map(String data) {
        try {
            var json = objectMapper.readTree(data);
            var rootNode = json.get("features").get(0);

            var properties = rootNode.get("properties");
            var geometry = rootNode.get("geometry");

            var distance = properties.get("distance").asDouble();
            var time = properties.get("time").asDouble();
            var points = mapPoints(geometry);

            return PathBoxed.builder()
                    .distance(distance)
                    .time(time)
                    .points(points)
                    .build();
        } catch (JsonProcessingException | NullPointerException e) {
            log.error("Failed to map Path", e);
            log.error("JSON data: {}", data);
            return PathBoxed.builder().build();
        }
    }

    private List<PointBoxed> mapPoints(JsonNode geometry) {
        var coordinates = geometry.get("coordinates");

        return stream(coordinates.spliterator(), false)
                .flatMap(node -> StreamSupport.stream(node.spliterator(), false))
                .map(this::mapPoint)
                .distinct()
                .toList();
    }

    private PointBoxed mapPoint(JsonNode data) {
        return PointBoxed.of(data.get(1).asDouble(), data.get(0).asDouble());
    }
}
