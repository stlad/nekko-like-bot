package ru.vaganov.nekkolike.contentmanager;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class LocalFileContentManager implements ContentManager {

    @Value("${content-manager.base-path}")
    private String basePath;


    @Override
    public void save(String filename, InputStream inputStream) throws IOException {
        var file = new File(String.format("%s/%s", basePath, filename));
        FileUtils.copyInputStreamToFile(inputStream, file);
        log.info("file {} saved at: {}", file.getName(), file.getAbsolutePath());
    }

    @Override
    public File load(String filename) {
        return null;
    }
}
