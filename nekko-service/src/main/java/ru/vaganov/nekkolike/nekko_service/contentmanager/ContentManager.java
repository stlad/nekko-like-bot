package ru.vaganov.nekkolike.nekko_service.contentmanager;

public interface ContentManager {

    void save(String filename, byte[] file);

    byte[] loadFile(String filePath);
}
