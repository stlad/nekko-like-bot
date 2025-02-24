package ru.vaganov.nekkolike.contentmanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface ContentManager {

    void save(String filename, InputStream inputStream) throws IOException;

    File load(String filename);
}
