package ru.vaganov.nekkolike.business.process.workflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.NekkoBot;
import ru.vaganov.nekkolike.bot.response.MessageBuilder;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.NekkoProcessState;
import ru.vaganov.nekkolike.business.process.utils.NekkoBotProcessUtils;
import ru.vaganov.nekkolike.processengine.context.ProcessContext;
import ru.vaganov.nekkolike.processengine.instance.ProcessInstance;
import ru.vaganov.nekkolike.processengine.state.NextStateRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartStep implements NekkoProcessStep {

    private final NekkoBot nekkoBot;
    private final ProcessContext processContext;
    private final ObjectMapper objectMapper;

    @Override
    public void execute(ProcessInstance processInstance) {
        log.info("Запущен процесс для чата {}", processInstance.getId());
        var data = NekkoBotProcessUtils.mapArguments(processInstance.getArgs(), objectMapper, UpdateData.class);
        nekkoBot.send(MessageBuilder.askForName(data.chatId()));
        processContext.waitNextState(processInstance.getId(), NekkoProcessState.WAIT_FOR_USERNAME, null);
    }

    @Override
    public NekkoProcessState getState() {
        return NekkoProcessState.START;
    }
}
