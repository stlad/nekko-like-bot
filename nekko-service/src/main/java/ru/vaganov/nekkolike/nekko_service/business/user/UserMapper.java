package ru.vaganov.nekkolike.nekko_service.business.user;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.nekko_service.business.user.dto.UserRegistrationDto;
import ru.vaganov.nekkolike.nekko_service.business.user.entity.User;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;

    public User fromDto(UserRegistrationDto dto) {
        return User.builder()
                .chatId(dto.getChatId())
                .username(dto.getUsername())
                .telegramUsername(dto.getTelegramUsername())
                .build();
    }
}
