package ru.vaganov.nekkolike.business.process.workflow;

import lombok.Getter;
import lombok.Setter;
import ru.vaganov.nekkolike.business.process.workflow.dto.*;
import ru.vaganov.nekkolike.business.process.workflow.dto.CatReviewDto;
import ru.vaganov.nekkolike.business.process.workflow.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.business.process.workflow.dto.UserRegistrationDto;

import java.util.UUID;

@Getter
public class UserWorkflow {

    private Long chatId;

    @Setter
    private WorkflowStep currentStep;

    private UserRegistrationDto userRegistrationDto;
    private CatRegistrationDto catRegistrationDto;
    private CatReviewDto catReviewDto;
    private MyCatsDto myCatsDto;
    private CatInfoDto catInfoDto;

    public UserWorkflow(Long chatId) {
        this.chatId = chatId;
    }

    public void initRegistration() {
        userRegistrationDto = new UserRegistrationDto(chatId);
    }

    public void initCat(UUID catId) {
        catRegistrationDto = new CatRegistrationDto(chatId, catId);
    }

    public void initCatList() {
        myCatsDto = new MyCatsDto();
    }
}
