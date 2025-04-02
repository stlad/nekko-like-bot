package ru.vaganov.nekkolike.bot.utils;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.HashMap;
import java.util.Map;

public record UpdateData(Long chatId, PhotoSize photo, String messageText) {

    public Map<String, Object> toArgs() {
        var map = new HashMap<String, Object>();
        map.put("chatId", chatId);
        map.put("messageText", messageText);
        map.put("photo", photo);
        return map;
    }
}
