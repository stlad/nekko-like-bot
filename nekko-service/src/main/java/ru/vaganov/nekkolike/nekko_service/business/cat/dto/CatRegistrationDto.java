package ru.vaganov.nekkolike.nekko_service.business.cat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CatRegistrationDto {

    @NotNull(message = "ИД чата с автором не может быть пустым")
    private Long authorChatId;

    @NotNull(message = "Фото котика не может быть пустым")
    private byte[] photo;

    @NotNull(message = "Имя котика не может быть пустым")
    private String catName;

    @NotNull(message = "ID котика не может быть пустым")
    private UUID catId;
}
