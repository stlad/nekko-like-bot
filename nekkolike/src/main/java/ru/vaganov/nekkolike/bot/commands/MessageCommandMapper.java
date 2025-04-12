package ru.vaganov.nekkolike.bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vaganov.nekkolike.bot.process.ProcessContext;
import ru.vaganov.nekkolike.bot.process.workflow.MainMenuState;
import ru.vaganov.nekkolike.bot.process.workflow.StartState;
import ru.vaganov.nekkolike.bot.process.workflow.WaitForUsernameState;
import ru.vaganov.nekkolike.bot.utils.TelegramBotUtils;

@RequiredArgsConstructor
@Component
public class MessageCommandMapper {
    private final ProcessContext processContext;

    public BotCommand extractCommand(Update update) {
        if (update.getMessage() != null && update.getMessage().hasPhoto()) {
            return BotCommand.SAVE_PHOTO;
        } else if (update.getCallbackQuery() != null) {
            return BotCommand.fromString(getParamFromCallback(update));
        } else if (update.getMessage() != null) {
            if (update.getMessage().getText() != null && !update.getMessage().getText().startsWith("/")) {
                var chatId = TelegramBotUtils.extractChatId(update);
                return getCommandByCurrentProcess(chatId);
            } else {
                return BotCommand.fromString(getParamFromMessage(update));
            }
        }
        return BotCommand.NONE;
    }

    private BotCommand getCommandByCurrentProcess(Long chatId) {
        var process = processContext.getProcess(chatId);
        if (processContext.isInState(process, StartState.class)) {
            return BotCommand.START;
        }
        if (processContext.isInState(process, WaitForUsernameState.class)) {
            return BotCommand.USERNAME_RECEIVED;
        }
        if (processContext.isInState(process, MainMenuState.class)) {
            return BotCommand.MOVE_TO_MAIN_MENU;
        }
        return BotCommand.NONE;
    }

    private String getParamFromCallback(Update update) {
        return update.getCallbackQuery().getData();
    }

    private String getParamFromMessage(Update update) {

        return update.getMessage().getText();
    }
}
