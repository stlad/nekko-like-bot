package ru.vaganov.nekkolike.nekko_service.business.cat.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.common.dto.CatInfoDto;
import ru.vaganov.nekkolike.nekko_service.contentmanager.ContentManager;

@Component
@RequiredArgsConstructor
public class CatRatingJdbcDao implements CatRatingDao {
    private final JdbcClient jdbcClient;
    private final ContentManager contentManager;

    @Override
    public CatInfoDto findRandom() {
        var cat = jdbcClient.sql("""
                        WITH cat_rates AS(
                        SELECT
                            cat.id AS cat_id,
                            COUNT(CASE WHEN review.rate = 'LIKE' THEN 1 ELSE NULL END) AS like_count,
                            COUNT(CASE WHEN review.rate = 'DISLIKE' THEN 1 ELSE NULL END) AS dislike_count
                        FROM t_cat cat
                        LEFT JOIN t_review review ON cat.id = review.link_cat
                        GROUP BY cat.id)
                        SELECT
                            cat.id AS cat_id,
                            cat.cat_name AS cat_name,
                            usr.telegram_username AS author_telegram_username,
                            usr.chat_id AS author_chat_id,
                            cat.photo_name AS photo_name,
                            rate.like_count AS like_count,
                            rate.dislike_count AS dislike_count
                        FROM cat_rates rate
                        INNER JOIN t_cat cat ON cat.id = rate.cat_id
                        INNER JOIN t_user usr ON usr.id = cat.link_user
                        ORDER BY RANDOM() LIMIT 1
                        """)
                .query(CatInfoDto.class)
                .single();
        var photo = contentManager.loadFile(cat.getPhotoName());
        cat.setPhoto(photo);
        return cat;
    }
}
