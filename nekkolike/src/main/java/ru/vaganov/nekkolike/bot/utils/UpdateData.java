package ru.vaganov.nekkolike.bot.utils;

import ru.vaganov.nekkolike.bot.exceptions.FileProcessingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public record UpdateData(Long chatId, String telegramUsername, File photo, String messageText, String[] params) {

    public byte[] getPhotoBytes() {
        if (photo == null) {
            return null;
        }
        try {
            return Files.readAllBytes(photo.toPath());
        } catch (IOException e) {
            throw new FileProcessingException("Не удалось обработать файл", e);
        }
    }
}
