package ru.vaganov.nekkolike.business.process.workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.NekkoBot;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.NekkoProcessState;
import ru.vaganov.nekkolike.business.process.utils.NekkoBotProcessUtils;
import ru.vaganov.nekkolike.processengine.instance.ProcessInstance;
import ru.vaganov.nekkolike.processengine.state.ProcessState;

@Component
@Slf4j
@RequiredArgsConstructor
public class WaitingForUsernameStep implements NekkoProcessStep {
    private final NekkoBot bot;
    private final ObjectMapper mapper;

    public void execute(ProcessInstance processInstance) {
        log.info("Запущен шаг {} для чата {}", getState(), processInstance.getId());
        var data = NekkoBotProcessUtils.mapArguments(processInstance.getArgs(), mapper, UpdateData.class);
        var username = data.messageText();

        bot.send(MessageBuilder.greetingsText(data.chatId(), username));
        bot.send(MessageBuilder.mainMenu(data.chatId()));

        processInstance.complete(NekkoProcessState.COMPLETE);
    }

    @Override
    public ProcessState getState() {
        return NekkoProcessState.WAIT_FOR_USERNAME;
    }
}
