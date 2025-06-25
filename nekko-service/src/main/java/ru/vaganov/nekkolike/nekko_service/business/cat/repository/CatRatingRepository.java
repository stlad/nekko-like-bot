package ru.vaganov.nekkolike.nekko_service.business.cat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.common.dto.CatInfoDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.dao.CatRatingDao;

@RequiredArgsConstructor
@Component
public class CatRatingRepository {

    private final CatRatingDao dao;

    public CatInfoDto findRandom() {
        return dao.findRandom();
    }
}
