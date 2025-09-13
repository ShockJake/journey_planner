package io.jp.app.optimization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jp.api.dto.RouteOptimizationRequest;
import io.jp.app.WebTestBase;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class OptimizationTestBase extends WebTestBase {
    private final LocalDateTime dateTime = LocalDateTime.parse("2025-08-31T10:10:00");
    private final String optimizationRequest = getOptimizationRequest();
    protected final MockHttpServletRequestBuilder request = post("/routes/optimize")
            .contentType(APPLICATION_JSON)
            .content(optimizationRequest);


    private String getOptimizationRequest() {
        try {
            String routeName = "Royal Kraków: Tracing the Kings’ Steps";
            return objectMapper.writeValueAsString(
                    new RouteOptimizationRequest(routeName, dateTime));
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
