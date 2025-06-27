package ru.vaganov.nekkolike.bot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTemplate {
    MAIN_MENU("Главное меню"),
    MY_CATS("Мои котики"),
    SHOW_CATS("Смотреть котиков"),
    SHOW_CATS_LIKE("\uD83D\uDC4D(%s)"),
    SHOW_CATS_DISLIKE("\uD83D\uDC4E(%s)"),
    ADD_CAT("Добавить котика"),
    NEXT("Далее"),
    PREV("Назад"),
    MY_CATS_LIST("Вот список ваших котиков. Для детальной информации по котику воспользуйтесь кнопкой"),
    MY_CATS_DELETE("Удаление котика"),
    ACCEPT("Подтвердить"),
    ASK_NAME("Как к Вам обращаться?"),
    ASK_CAT_NAME("Введите имя котика"),
    ASK_CAT_PHOTO("Отправьте фото котика"),
    ASK_CAT_START_FROM_BEGINNING("Начать заново"),
    ADD_CAT_ACCEPT_TEXT("Котик: %s \n Автор: @%s"),
    ADD_CAT_CREATED("Ура! Котик создан!"),
    GREETINGS("Привет, %s!");
    private final String template;


    public static String apply(MessageTemplate template, Object... args) {
        return String.format(template.getTemplate(), args);
    }
}
