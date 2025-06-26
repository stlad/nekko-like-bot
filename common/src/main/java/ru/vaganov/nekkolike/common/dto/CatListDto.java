package ru.vaganov.nekkolike.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatListDto {
    private Long authorChatId;

    private List<CatListElementDto> cats;

    private Integer page;

    private Integer pageSize;
}
