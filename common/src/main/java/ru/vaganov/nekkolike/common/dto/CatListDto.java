package ru.vaganov.nekkolike.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CatListDto {
    private Long authorChatId;

    private List<CatListElementDto> cats;

    private Integer page;

    private Integer pageSize;

    public CatListDto() {
        page = 0;
        pageSize = 9;
    }

    public void nextPage() {
        page += 1;
    }

    public void prevPage() {
        if (page == 0) {
            return;
        }
        page -= 1;
    }

}
