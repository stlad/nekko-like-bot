package ru.vaganov.nekkolike.contentmanager;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Component
public class LocalFileContentManager implements ContentManager {

    @Value("${content-manager.base-path}")
    private String basePath;


    @Override
    public void save(String filename, InputStream inputStream) throws IOException {
        var file = new File(String.format("%s/%s", basePath, filename));
        FileUtils.copyInputStreamToFile(inputStream, file);
        log.info("Файл {} сахранен в: {}", file.getName(), file.getAbsolutePath());
    }

    @Override
    public List<File> loadAllFiles(String directoryPath) throws IOException {
        log.trace("Загрузка файлов из директории {}", directoryPath);
        try (var stream = Files.list(Paths.get(basePath + directoryPath))) {
            return stream
                    .filter(path -> !Files.isDirectory(path))
                    .map(Path::toFile)
                    .toList();
        }
    }
}
