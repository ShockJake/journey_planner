package io.jp.integration.common;

import org.springframework.web.client.RestClient;

public class RestClientProvider {
    public static RestClient getRestClient() {
        return RestClient.builder().build();
    }
}
