package ru.vaganov.nekkolike.bot;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vaganov.nekkolike.bot.commands.MessageCommandMapper;
import ru.vaganov.nekkolike.bot.service.CommandExecutor;
import ru.vaganov.nekkolike.bot.utils.SendObjectWrapper;
import ru.vaganov.nekkolike.bot.utils.TelegramBotUtils;

@Component
@RequiredArgsConstructor
public class NekkoBot extends TelegramLongPollingBot {

    private final Logger logger = LoggerFactory.getLogger(NekkoBot.class);

    @Value("${telegram.name}")
    private String name;

    @Value("${telegram.token}")
    private String token;

    private final CommandExecutor commandExecutor;
    private final ResponseSender responseSender;

    private void processUpdate(Update update) {
        var command = MessageCommandMapper.extractCommand(update);
        var updateData = TelegramBotUtils.updateData(update);
        var response = commandExecutor.executeCommand(command, updateData, this);
        this.send(response);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            logger.info("Message: {} from {}", update.getMessage().getText(), update.getMessage().getChatId());
        }
        if (update.getCallbackQuery() != null) {
            logger.info("CallbackQuery: {} from {}", update.getCallbackQuery().getData(), update.getCallbackQuery().getMessage().getChatId());
        }
        processUpdate(update);
    }

    private void send(SendObjectWrapper wrapper) {
        try {
            if (wrapper.getSendMessage() != null) {
                this.execute(wrapper.getSendMessage());
            }
            if (wrapper.getSendPhoto() != null) {
                this.execute(wrapper.getSendPhoto());
            }
        } catch (Throwable e) {
            logger.error("Ошибка при отправке сообщения в чат {}", wrapper.getChatId(), e);
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}
