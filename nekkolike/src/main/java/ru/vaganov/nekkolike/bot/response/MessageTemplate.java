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
    ACCEPT("Подтвердить"),
    ASK_NAME("Как к Вам обращаться?"),
    ASK_CAT_NAME("Введите имя котика"),
    ASK_CAT_PHOTO("Отправьте фото котика"),
    ASK_CAT_START_FROM_BEGINNING("Начать заново"),
    ADD_CAT_ACCEPT_TEXT("Котик: %s \n Автор: @%s"),
    ADD_CAT_CREATED("Ура! Котик создан!"),
    GREETINGS("Привет, %s!")
    ;
    private final String template;


    public static String apply(MessageTemplate template, Object... args) {
        return String.format(template.getTemplate(), args);
    }
}
