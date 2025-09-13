package io.jp.app.generation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jp.IntegrationTest;
import io.jp.api.dto.RouteGenerationRequest;
import io.jp.app.WebTestBase;
import io.jp.core.domain.route.RouteLongevity;
import io.jp.database.entities.route.PlaceType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.List;

import static io.jp.core.domain.route.RouteLongevity.LONG;
import static io.jp.database.entities.route.PlaceType.PARK;
import static io.jp.database.entities.route.PlaceType.THEATRE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class GenerationTestBase extends WebTestBase {
    private final String generationRequest = getGenerationRequest();
    protected final MockHttpServletRequestBuilder request = post("/routes/generate")
            .contentType(APPLICATION_JSON)
            .content(generationRequest);

    private String getGenerationRequest() {
        try {
            var placeTypes = List.of(THEATRE, PARK);
            var generationRequest = new RouteGenerationRequest(false, LONG, "Kraków",
                    placeTypes, "2025-08-31T10:10:00");
            return objectMapper.writeValueAsString(generationRequest);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    protected void initializeMunicipalities(MockMvc mvc) throws Exception {
        var request = get("/routes/generate/metadata")
                .contentType(APPLICATION_JSON);
        mvc.perform(request);
    }
}
