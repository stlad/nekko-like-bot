package ru.vaganov.nekkolike.nekko_service.business.cat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CatReviewDto {

    private Long authorChatId;

    private String authorTelegramUsername;

    private UUID catId;

    private String photoName;

    private byte[] photo;

    private String catName;

    private Integer likeCount;

    private Integer dislikeCount;
}
