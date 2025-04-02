package ru.vaganov.nekkolike.bot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BotCommand {

    START("start"),
    MOVE_TO_MAIN_MENU("main_menu"),
    SAVE_PHOTO(null),
    GET_PHOTO("photo"),
    GET_PHOTO_NEXT("photo_next"),
    GET_PHOTO_PREV("photo_prev"),
    NONE(null);

    private final String callbackPrefix;

    public static BotCommand fromString(String str) {
        return Arrays.stream(BotCommand.values())
                .filter(cmd -> cmd.getCallbackPrefix() != null)
                .filter(cmd -> cmd.callbackPrefix.startsWith(str)).findAny().orElse(NONE);
    }
}
