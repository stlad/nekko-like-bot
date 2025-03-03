package ru.vaganov.nekkolike.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum BotCommand {

    MOVE_TO_MAIN_MENU(Set.of("/main_menu")),
    SAVE_PHOTO(Collections.emptySet()),
    GET_ALL_PHOTOS(Set.of("Посмотреть мои картинки", "/show_my_photos")),
    NONE(Collections.emptySet());

    private final Set<String> possibleCallbacks;

    public static BotCommand fromCallBack(String callback) {
        return Arrays.stream(BotCommand.values()).filter(cmd -> cmd.getPossibleCallbacks().contains(callback)).findAny()
                .orElse(NONE);
    }
}
