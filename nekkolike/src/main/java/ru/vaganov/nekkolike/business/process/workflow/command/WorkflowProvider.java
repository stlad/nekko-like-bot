package ru.vaganov.nekkolike.business.process.workflow.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.exceptions.CommandNotFoundException;
import ru.vaganov.nekkolike.bot.response.TelegramMessageSender;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.workflow.WorkflowStep;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class WorkflowProvider {
    private final Map<WorkflowStep, WorkflowCommand> commands;

    public WorkflowProvider(List<WorkflowCommand> commandList) {
        this.commands = new HashMap<>();
        commandList.forEach(
                cmd -> commands.put(cmd.getInitStep(), cmd)
        );
    }

    public void execute(WorkflowStep step, UpdateData updateData, TelegramMessageSender sender) {
        if (!commands.containsKey(step)) {
            throw new CommandNotFoundException(step.name());
        }
        commands.get(step).execute(updateData, sender);
    }
}
