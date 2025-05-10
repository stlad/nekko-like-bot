package ru.vaganov.nekkolike.bot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTemplate {
    MAIN_MENU("Главное меню"),
    MY_CATS("Мои котики"),
    SHOW_CATS("Смотреть котиков"),
    ADD_CAT("Добавить котика"),
    NEXT("Далее"),
    PREV("Назад"),
    ASK_NAME("Как к Вам обращаться?"),
    GREETINGS("Привет, %s!")
    ;
    private final String template;


    public static String apply(MessageTemplate template, Object... args) {
        return String.format(template.getTemplate(), args);
    }
}
