package io.jp.integration.provider;

import java.util.Map;

public interface DataProvider<T> {
    T getData(Map<String, Object> input);
}
