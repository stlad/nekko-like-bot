package ru.vaganov.nekkolike.business.process.workflow;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.vaganov.nekkolike.business.process.workflow.dto.UserRegistrationDto;

@Getter
public class UserWorkflow {

    private Long chatId;

    @Setter
    private WorkflowStep currentStep;

    private UserRegistrationDto userRegistrationDto;

    public UserWorkflow(Long chatId) {
        this.chatId = chatId;
    }

    public void initRegistration(){
        userRegistrationDto = new UserRegistrationDto(chatId);
    }
}
