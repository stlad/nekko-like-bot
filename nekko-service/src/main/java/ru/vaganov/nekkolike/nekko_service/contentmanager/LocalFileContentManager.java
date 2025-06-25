package ru.vaganov.nekkolike.nekko_service.contentmanager;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.nekko_service.exception.ContentManagerException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
public class LocalFileContentManager implements ContentManager {

    @Value("${content-manager.base-path}")
    private String basePath;


    @Override
    public void save(String filename, byte[] fileData) {
        var file = new File(String.format("%s/%s", basePath, filename));
        try {
            FileUtils.copyInputStreamToFile(new ByteArrayInputStream(fileData), file);
        } catch (IOException e) {
            throw new ContentManagerException(filename, e);
        }
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
