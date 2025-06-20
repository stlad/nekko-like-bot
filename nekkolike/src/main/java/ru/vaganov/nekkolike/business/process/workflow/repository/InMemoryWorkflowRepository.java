package ru.vaganov.nekkolike.business.process.workflow.repository;

import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.business.process.workflow.UserWorkflow;
import ru.vaganov.nekkolike.business.process.workflow.WorkflowStep;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryWorkflowRepository implements WorkflowRepository {
    private final Map<Long, UserWorkflow> flows;

    public InMemoryWorkflowRepository() {
        flows = new HashMap<>();
    }

    @Override
    public Optional<UserWorkflow> findByChatId(Long chatId) {
        if (flows.containsKey(chatId)) {
            return Optional.of(flows.get(chatId));
        }
        return Optional.empty();
    }

    @Override
    public UserWorkflow saveFlow(UserWorkflow userWorkflow) {
        flows.put(userWorkflow.getChatId(), userWorkflow);
        return userWorkflow;
    }

    @Override
    public Optional<WorkflowStep> findCurrentStepByChatId(Long chatId) {
        if (flows.containsKey(chatId)) {
            return Optional.of(flows.get(chatId).getCurrentStep());
        }
        return Optional.empty();
    }
}
