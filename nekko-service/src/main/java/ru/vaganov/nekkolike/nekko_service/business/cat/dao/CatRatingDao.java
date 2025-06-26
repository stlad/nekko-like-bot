package ru.vaganov.nekkolike.nekko_service.business.cat.dao;

import ru.vaganov.nekkolike.common.dto.CatInfoDto;

public interface CatRatingDao {
    CatInfoDto findRandom();
}
