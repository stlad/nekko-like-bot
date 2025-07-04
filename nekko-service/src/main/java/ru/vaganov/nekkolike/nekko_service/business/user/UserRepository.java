package ru.vaganov.nekkolike.nekko_service.business.user;

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

    @Query("""
            SELECT count(u.id) > 0 FROM User u WHERE u.chatId = :chatId
            """)
    boolean existByChatId(Long chatId);
}
