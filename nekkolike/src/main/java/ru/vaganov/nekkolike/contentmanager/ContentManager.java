package ru.vaganov.nekkolike.contentmanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ContentManager {

    void save(String filename, InputStream inputStream) throws IOException;

    File load(String filename);

    List<File> loadAllFiles(String directoryPath) throws IOException;
}
