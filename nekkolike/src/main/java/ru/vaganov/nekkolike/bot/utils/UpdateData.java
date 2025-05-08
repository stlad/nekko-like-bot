package ru.vaganov.nekkolike.bot.utils;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.Map;

public record UpdateData(Long chatId, PhotoSize photo, String messageText) {

    public Map<String, Object> toArgs() {
        return Map.ofEntries(
                Map.entry("chatId", chatId),
                Map.entry("messageText", messageText),
                Map.entry("photo", photo)
        );
    }
}
