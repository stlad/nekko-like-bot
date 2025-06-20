package ru.vaganov.nekkolike.nekko_service.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vaganov.nekkolike.nekko_service.cat.entity.Cat;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface CatRepository extends JpaRepository<Cat, UUID> {

    @Query("""
            SELECT c.catName FROM Cat c WHERE c.user.chatId = :chatId
            """)
    Stream<String> findCatNamesByChatId(Long chatId);
}
