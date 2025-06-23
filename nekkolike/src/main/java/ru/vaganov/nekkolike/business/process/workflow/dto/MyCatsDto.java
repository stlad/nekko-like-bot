package ru.vaganov.nekkolike.business.process.workflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class MyCatsDto {

    private Long authorChatId;

    private List<CatListElement> cats;

    private Integer page;

    private Integer pageSize;

    public MyCatsDto() {
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
        page += 1;
    }

    public record CatListElement(String catName, UUID catId) {
    }
}
