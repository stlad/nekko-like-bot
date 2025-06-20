package ru.vaganov.nekkolike.business.process.workflow.command;

import ru.vaganov.nekkolike.bot.response.TelegramMessageSender;
import ru.vaganov.nekkolike.bot.utils.UpdateData;
import ru.vaganov.nekkolike.business.process.workflow.WorkflowStep;

public interface WorkflowCommand {

    void execute(UpdateData data, TelegramMessageSender sender);

    WorkflowStep getInitStep();
}
