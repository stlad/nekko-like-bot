package ru.vaganov.nekkolike.bot.commands;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vaganov.nekkolike.bot.exceptions.CommandNotFoundException;

@RequiredArgsConstructor
public class MessageCommandMapper {

    public static BotCommand extractCommand(Update update) {
        if (update.getMessage().hasPhoto()) {
            return BotCommand.SAVE_PHOTO;
        } else if (update.getCallbackQuery() != null) {
            return BotCommand.fromCallBack(getParamFromCallback(update, 1));
        } else if (update.getMessage() != null) {
            return BotCommand.fromCallBack(getParamFromMessage(update, 1));
        }
        return BotCommand.NONE;
    }

    public static PhotoPage extractPhotoPage(Update update) {
        if (!extractCommand(update).equals(BotCommand.GET_PHOTO)) {
            throw new CommandNotFoundException(update.getCallbackQuery().getData());
        }
        return PhotoPage.valueOf(update.getCallbackQuery().getData().split("/")[2]);
    }

    private static String getParamFromCallback(Update update, int param) {
        return update.getCallbackQuery().getData().split("/")[param];
    }

    private static String getParamFromMessage(Update update, int param) {
        return update.getMessage().getText().split("/")[param];
    }
}
