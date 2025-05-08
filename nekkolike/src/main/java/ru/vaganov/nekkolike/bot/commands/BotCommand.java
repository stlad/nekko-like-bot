package ru.vaganov.nekkolike.bot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BotCommand {

    START("start"),
    MOVE_TO_MAIN_MENU("main_menu"),
    USER_MESSAGE(null),
    NONE(null);

    private final String callbackPrefix;

    public static BotCommand fromString(String str) {
        var formattedCommand = str.replace("/", "");
        return Arrays.stream(BotCommand.values())
                .filter(cmd -> cmd.getCallbackPrefix() != null)
                .filter(cmd -> cmd.callbackPrefix.startsWith(formattedCommand)).findAny().orElse(NONE);
    }
}
