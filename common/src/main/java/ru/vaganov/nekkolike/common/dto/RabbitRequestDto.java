package ru.vaganov.nekkolike.common.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

//TODO данный класс должен лежать в пакете с backendClient
@Getter
@Setter
@Data
@Builder
public class RabbitRequestDto {
    private RequestAction action;
    private Long chatId;
    private UUID catId;
    private String telegramUsername;
    private String errorText;

    private CatRegistrationDto catRegistrationDto;
    private UserRegistrationDto userRegistrationDto;
    private Integer page;
    private Integer pageSize;

    public static RabbitRequestDto likeCat(Long chatId, UUID catId) {
        return RabbitRequestDto.
                builder()
                .action(RequestAction.LIKE_CAT_REQUEST)
                .chatId(chatId)
                .catId(catId)
                .build();
    }

    public static RabbitRequestDto dislikeCat(Long chatId, UUID catId) {
        return RabbitRequestDto.
                builder()
                .action(RequestAction.DISLIKE_CAT_REQUEST)
                .chatId(chatId)
                .catId(catId)
                .build();
    }

    public static RabbitRequestDto registerCat(Long chatId, CatRegistrationDto dto) {
        return RabbitRequestDto.
                builder()
                .action(RequestAction.REGISTER_CAT_REQUEST)
                .chatId(chatId)
                .catId(dto.getCatId())
                .catRegistrationDto(dto)
                .build();
    }

    public static RabbitRequestDto registerUser(Long chatId, UserRegistrationDto dto) {
        return RabbitRequestDto.
                builder()
                .action(RequestAction.REGISTER_USER_REQUEST)
                .chatId(chatId)
                .userRegistrationDto(dto)
                .build();
    }

    public static RabbitRequestDto randomCat(Long chatId) {
        return RabbitRequestDto.
                builder()
                .action(RequestAction.GET_RANDOM_CAT_REQUEST)
                .chatId(chatId)
                .build();
    }

    public static RabbitRequestDto concreteCat(Long chatId, UUID catId) {
        return RabbitRequestDto.
                builder()
                .action(RequestAction.GET_CONCRETE_CAT_REQUEST)
                .chatId(chatId)
                .catId(catId)
                .build();
    }

    public static RabbitRequestDto deleteCat(Long chatId, UUID catId) {
        return RabbitRequestDto.
                builder()
                .action(RequestAction.DELETE_CAT_REQUEST)
                .chatId(chatId)
                .catId(catId)
                .build();
    }

    public static RabbitRequestDto catPage(Long chatId, Integer page, Integer pageSize) {
        return RabbitRequestDto.
                builder()
                .action(RequestAction.GET_CAT_LIST_REQUEST)
                .chatId(chatId)
                .page(page)
                .pageSize(pageSize)
                .build();
    }
}
