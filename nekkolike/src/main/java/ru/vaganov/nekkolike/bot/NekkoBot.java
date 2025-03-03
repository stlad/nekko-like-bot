package ru.vaganov.nekkolike.bot;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vaganov.nekkolike.bot.commands.CommandExecutor;
import ru.vaganov.nekkolike.bot.request.RequestHandler;
import ru.vaganov.nekkolike.bot.response.ResponseSender;

@Component
@RequiredArgsConstructor
public class NekkoBot extends TelegramLongPollingBot {

    private final Logger logger = LoggerFactory.getLogger(NekkoBot.class);

    @Value("${telegram.name}")
    private String name;

    @Value("${telegram.token}")
    private String token;

    private final RequestHandler requestHandler;
    private final CommandExecutor commandExecutor;
    private final ResponseSender responseSender;

    private void processUpdate(Update update) {
        var command = requestHandler.mapToCommand(update);
        var response = commandExecutor.executeCommand(command, update, this);
        responseSender.send(response, this);
    }



    @Override
    public void onUpdateReceived(Update update) {
        logger.info("{} from {}", update.getMessage().getText(), update.getMessage().getChatId());
        processUpdate(update);
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
