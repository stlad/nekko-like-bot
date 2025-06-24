package ru.vaganov.nekkolike.nekko_service.business.cat.dto;

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
