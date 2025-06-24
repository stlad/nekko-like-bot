package ru.vaganov.nekkolike.nekko_service.contentmanager;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.nekko_service.exception.ContentManagerException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    public byte[] loadFile(String filePath) {
        var fullpath = basePath + filePath;
        log.trace("Загрузка файла {}", fullpath);
        try {
            return Files.readAllBytes(Paths.get(fullpath));
        } catch (IOException e) {
            throw new ContentManagerException(filePath, e);
        }
    }
}
