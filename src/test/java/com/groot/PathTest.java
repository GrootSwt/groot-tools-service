package com.groot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest
public class PathTest {

    @Value("${application.file-root-path}")
    private String fileRootPath;

    @Test
    public void pathTest() throws IOException {
        Path path = Paths.get(fileRootPath, "./2023-08-29");
        System.out.println(path.getParent());
        if (Files.exists(path) && Files.isDirectory(path)) {
            try (Stream<Path> list = Files.list(path)) {
                Set<Path> collect = list.collect(Collectors.toSet());
                if (collect.isEmpty()) {
                    Files.delete(path);
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
