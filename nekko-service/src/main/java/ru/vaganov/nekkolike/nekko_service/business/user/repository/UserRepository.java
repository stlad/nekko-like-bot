package ru.vaganov.nekkolike.nekko_service.business.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vaganov.nekkolike.nekko_service.business.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {


    @Query("""
            SELECT u FROM User u WHERE u.chatId = :chatId
            """)
    Optional<User> findByChatId(Long chatId);
}
