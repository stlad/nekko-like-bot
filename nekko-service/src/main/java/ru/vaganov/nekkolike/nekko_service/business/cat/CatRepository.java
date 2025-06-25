package ru.vaganov.nekkolike.nekko_service.business.cat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vaganov.nekkolike.common.dto.CatListElementDto;
import ru.vaganov.nekkolike.common.dto.CatInfoDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.entity.Cat;

import java.util.UUID;

public interface CatRepository extends JpaRepository<Cat, UUID> {

    @Query("""
            SELECT new ru.vaganov.nekkolike.common.dto.CatListElementDto(c.catName, c.id)
            FROM Cat c WHERE c.user.chatId = :chatId
            """)
    Page<CatListElementDto> findCatNamesByChatId(Long chatId, Pageable pageable);

    @Query(value = """
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
                usr.telegram_username AS telegram_username,
                usr.chat_id AS chat_id,
                cat.photo_name AS photo_name,
                rate.like_count AS like_count,
                rate.dislike_count AS dislike_count
            FROM cat_rates rate
            INNER JOIN t_cat cat ON cat.id = rate.cat_id
            INNER JOIN t_user usr ON usr.id = cat.link_user
            ORDER BY RANDOM() LIMIT 1
            """, nativeQuery = true)
    CatInfoDto findRandomCat();
}
