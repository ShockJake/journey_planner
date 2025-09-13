package io.jp.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.concurrent.Callable;

public class WebTestBase {
    protected final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    protected Callable<String> getRequestAction(MockMvc mockMvc, MockHttpServletRequestBuilder request, ResultMatcher resultMatcher) {
        return () -> {
            try {
                mockMvc.perform(request)
                        .andExpect(resultMatcher)
                        .andReturn();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            return "";
        };
    }
}
