package io.jp.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static java.nio.charset.Charset.defaultCharset;

public class FileUtils {
    public String readFile(String filePath) {
        return Optional.ofNullable(this.getClass().getResourceAsStream(filePath))
                .flatMap(this::readDataFromResource)
                .orElseThrow(() -> new RuntimeException("Cannot read file " + filePath));
    }

    public void createFile(String filePath) {
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Cannot create file " + filePath + ": " + e.getMessage());
        }
    }

    public void appendToFile(String filePath, String content) {
        try (var writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Cannot write to file " + filePath + ": " + e.getMessage());
        }
    }

    private Optional<String> readDataFromResource(InputStream inputStream) {
        try {
            return Optional.of(IOUtils.toString(inputStream, defaultCharset()));
        } catch (IOException exception) {
            return Optional.empty();
        }
    }
}
