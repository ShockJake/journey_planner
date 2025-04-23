package io.jp.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static java.nio.charset.Charset.defaultCharset;

public class FileUtils {
    public String readFile(String filePath) {
        return Optional.ofNullable(this.getClass().getResourceAsStream(filePath))
                .flatMap(this::readDataFromResource)
                .orElseThrow(() -> new RuntimeException("Cannot read file " + filePath));
    }

    private Optional<String> readDataFromResource(InputStream inputStream) {
        try {
            return Optional.of(IOUtils.toString(inputStream, defaultCharset()));
        } catch (IOException exception) {
            return Optional.empty();
        }
    }
}
