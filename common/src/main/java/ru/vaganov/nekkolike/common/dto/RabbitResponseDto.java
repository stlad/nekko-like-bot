package ru.vaganov.nekkolike.common.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class RabbitResponseDto {
    private ResponseAction action;
    private Long chatId;
    private String telegramUsername;
    private String errorText;

    private CatInfoDto catInfoDto;
    private CatListDto catListDto;


    public static RabbitResponseDto randomCat(Long chatId, CatInfoDto dto) {
        return RabbitResponseDto.builder()
                .chatId(chatId)
                .catInfoDto(dto)
                .action(ResponseAction.GET_RANDOM_CAT_RESPONSE)
                .build();
    }

    public static RabbitResponseDto concreteCat(Long chatId, CatInfoDto dto) {
        return RabbitResponseDto.builder()
                .chatId(chatId)
                .catInfoDto(dto)
                .action(ResponseAction.GET_CONCRETE_CAT_RESPONSE)
                .build();
    }

    public static RabbitResponseDto catList(Long chatId, CatListDto dto) {
        return RabbitResponseDto.builder()
                .chatId(chatId)
                .catListDto(dto)
                .action(ResponseAction.GET_CAT_LIST_RESPONSE)
                .build();
    }

    public static RabbitResponseDto error(Long chatId, String errorText) {
        return RabbitResponseDto.builder()
                .chatId(chatId)
                .errorText(errorText)
                .action(ResponseAction.ERROR)
                .build();
    }
}
