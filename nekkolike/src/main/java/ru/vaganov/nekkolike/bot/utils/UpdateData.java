package ru.vaganov.nekkolike.bot.utils;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;

public record UpdateData(Long chatId, PhotoSize photo, String messageText) {
}
