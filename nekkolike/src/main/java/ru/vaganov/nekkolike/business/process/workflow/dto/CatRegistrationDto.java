package ru.vaganov.nekkolike.business.process.workflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Setter
@Getter
public class CatRegistrationDto {

    private final Long authorChatId;

    private File photo;

    private String catName;

    public CatRegistrationDto(Long authorChatId) {
        this.authorChatId = authorChatId;
    }
}
