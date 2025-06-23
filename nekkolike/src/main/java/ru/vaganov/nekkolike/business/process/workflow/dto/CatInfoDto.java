package ru.vaganov.nekkolike.business.process.workflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.UUID;

@Setter
@Getter
public class CatInfoDto {

    private Long authorChatId;

    private String authorTelegramUsername;

    private UUID catId;

    private File photo;

    private String catName;
}
