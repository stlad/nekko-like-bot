package ru.vaganov.nekkolike.business.process.workflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.UUID;

@Setter
@Getter
public class CatRegistrationDto {

    private final Long authorChatId;

    private UUID catId;

    private File photo;

    private String catName;

    public CatRegistrationDto(Long authorChatId, UUID catId) {
        this.authorChatId = authorChatId;
        this.catId = catId;
    }
}
