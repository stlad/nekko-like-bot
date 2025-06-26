package ru.vaganov.nekkolike.nekko_service.business.cat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vaganov.nekkolike.common.dto.CatListElementDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.entity.Cat;

import java.util.UUID;

public interface CatRepository extends JpaRepository<Cat, UUID> {

    @Query("""
            SELECT new ru.vaganov.nekkolike.common.dto.CatListElementDto(c.catName, c.id)
            FROM Cat c WHERE c.user.chatId = :chatId
            """)
    Page<CatListElementDto> findCatNamesByChatId(Long chatId, Pageable pageable);
}
