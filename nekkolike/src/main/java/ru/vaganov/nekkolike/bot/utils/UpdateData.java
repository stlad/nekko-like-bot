package ru.vaganov.nekkolike.bot.utils;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.HashMap;
import java.util.Map;

public record UpdateData(Long chatId, String telegramUsername, PhotoSize photo, String messageText) {

}
