package ru.vaganov.nekkolike.nekko_service.business.cat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReviewRateDto {
    private UUID catId;
    private Integer likeCount;
    private Integer dislikeCount;
}
