package ru.vaganov.nekkolike.bot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BotCommand {

    MOVE_TO_MAIN_MENU("/main_menu"),
    SAVE_PHOTO(null),
    GET_PHOTO("/photo"),
    NONE(null);

    private final String callbackPrefix;

    public static BotCommand fromCallBack(String callback) {
        var rawCallBack = callback.split("/")[0];
        return Arrays.stream(BotCommand.values()).filter(cmd ->
                        cmd.callbackPrefix.startsWith(rawCallBack)).findAny()
                .orElse(NONE);
    }
}
