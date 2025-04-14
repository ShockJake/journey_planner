package io.jp.rest.provider;

import java.util.Map;

public interface DataProvider<T> {
    T getData(Map<String, String> input);
}
