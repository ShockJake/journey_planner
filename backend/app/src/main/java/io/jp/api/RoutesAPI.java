package io.jp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.rest.provider.DataProvider;
import io.jp.rest.response.PlacesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RoutesAPI {
    private final DataProvider<PlacesResponse> dataProvider;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<String> getRoutes() throws JsonProcessingException {
        var places = dataProvider.getData(Map.of()).getPlaces();
        log.info(places.toString());
        var result = objectMapper.writeValueAsString(places);
        log.info(result);
        return ResponseEntity.ok(result);
    }
}
