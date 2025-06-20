package ru.vaganov.nekkolike.bot.utils;

import java.io.File;
import java.util.List;

public record UpdateData(Long chatId, String telegramUsername, File photo, String messageText, String[] params) {

}
