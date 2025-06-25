package ru.vaganov.nekkolike.nekko_service.business.review.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository  extends JpaRepository<Review, UUID> {

    @Query("""
            SELECT r FROM Review r
            WHERE r.user.chatId = :chatId AND r.cat.id = :catId
            """)
    Optional<Review> findByUserChatId(Long chatId, UUID catId);
}
