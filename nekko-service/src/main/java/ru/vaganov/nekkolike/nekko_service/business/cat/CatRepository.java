package ru.vaganov.nekkolike.nekko_service.business.cat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatListDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatListElementDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.ReviewRateDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.entity.Cat;

import java.util.Optional;
import java.util.UUID;

public interface CatRepository extends JpaRepository<Cat, UUID> {

    @Query("""
            SELECT new ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatListElementDto(c.catName, c.id)
            FROM Cat c WHERE c.user.chatId = :chatId
            """)
    Page<CatListElementDto> findCatNamesByChatId(Long chatId, Pageable pageable);

    @Query(value = """
            SELECT * FROM t_cat ORDER BY RANDOM() LIMIT 1
            """, nativeQuery = true)
    Optional<Cat> findRandomCat();

    @Query(value = """
            SELECT
                cat.id AS cat_id,
                COUNT(CASE WHEN review.rate = 'LIKE' THEN 1 ELSE NULL END) AS like_count,
                COUNT(CASE WHEN review.rate = 'DISLIKE' THEN 1 ELSE NULL END) AS dislike_count
            FROM t_cat cat
            INNER JOIN t_review review ON cat.id = review.link_cat
            WHERE cat.id = :catId
            GROUP BY cat.id
            """, nativeQuery = true)
    ReviewRateDto getRates(UUID catId);

}
