package ru.vaganov.nekkolike.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatInfoDto {

    private Long authorChatId;

    private String authorTelegramUsername;

    private UUID catId;

    private String photoName;

    private byte[] photo;

    private String catName;

    private Integer likeCount;

    private Integer dislikeCount;
}
