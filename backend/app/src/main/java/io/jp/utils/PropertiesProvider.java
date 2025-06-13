package io.jp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PropertiesProvider {
    public static final String ROUTING_API_KEY_PROPERTY_NAME = "geoApifyKey";
    private final Map<String, String> properties;

    public PropertiesProvider(ObjectMapper objectMapper) {
        var data = new FileUtils().readFile("/properties.json");
        try {
            properties = readProperties(objectMapper, data);
            log.info("Loaded properties from file: {}", properties.size());
        } catch (JsonProcessingException e) {
            log.error("Cannot parse properties file", e);
            throw new RuntimeException("Cannot parse properties file");
        }
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    private Map<String, String> readProperties(ObjectMapper mapper, String data) throws JsonProcessingException {
        var json = mapper.readTree(data);
        return json.properties().stream()
                .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().asText()));
    }
}
