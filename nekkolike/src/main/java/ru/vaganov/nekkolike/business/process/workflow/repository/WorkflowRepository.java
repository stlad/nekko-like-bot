package ru.vaganov.nekkolike.business.process.workflow.repository;

import ru.vaganov.nekkolike.business.process.workflow.UserWorkflow;
import ru.vaganov.nekkolike.business.process.workflow.WorkflowStep;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface WorkflowRepository {

    Optional<UserWorkflow> findByChatId(Long chatId);

    UserWorkflow saveFlow(UserWorkflow userWorkflow);

    Optional<WorkflowStep> findCurrentStepByChatId(Long chatId);
}
