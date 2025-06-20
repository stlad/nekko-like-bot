package ru.vaganov.nekkolike.business.process.workflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserRegistrationDto {
    private final Long chatId;

    @Setter
    private String username;

    public UserRegistrationDto(Long chatId) {
        this.chatId = chatId;
    }
}
