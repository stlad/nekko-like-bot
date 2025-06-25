package ru.vaganov.nekkolike.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Builder
public class CatRegistrationDto {

    private Long authorChatId;

    private UUID catId;

    private byte[] photo;

    private String catName;
}
