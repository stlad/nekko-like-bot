package ru.vaganov.nekkolike.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CatListDto {
    private Long authorChatId;

    private List<CatListElementDto> cats;

    private Integer page;

    private Integer pageSize;

}
