package ru.vaganov.nekkolike.nekko_service.contentmanager;

import java.io.IOException;
import java.io.InputStream;

public interface ContentManager {

    void save(String filename, InputStream inputStream) throws IOException;

    byte[] loadFile(String filePath);
}
