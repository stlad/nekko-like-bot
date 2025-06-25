package ru.vaganov.nekkolike.business.process.workflow;

import lombok.Getter;
import lombok.Setter;
import ru.vaganov.nekkolike.common.dto.CatInfoDto;
import ru.vaganov.nekkolike.common.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.common.dto.UserRegistrationDto;

import java.util.UUID;

@Getter
@Setter
public class UserWorkflow {

    private Long chatId;

    @Setter
    private WorkflowStep currentStep;

    private UserRegistrationDto userRegistrationDto;
    private CatRegistrationDto catRegistrationDto;
    private CatInfoDto catInfoDto;

    public UserWorkflow(Long chatId) {
        this.chatId = chatId;
    }

    public void initRegistration(Long chatId, String telegramUsername) {
        userRegistrationDto = UserRegistrationDto.builder().chatId(chatId).telegramUsername(telegramUsername).build();
    }

    public void initCat(UUID catId) {
        catRegistrationDto = CatRegistrationDto.builder().catId(catId).authorChatId(chatId).build();
    }
}
