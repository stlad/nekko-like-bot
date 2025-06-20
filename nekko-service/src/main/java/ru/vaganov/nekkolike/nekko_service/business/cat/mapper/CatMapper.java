package ru.vaganov.nekkolike.nekko_service.business.cat.mapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.entity.Cat;
import ru.vaganov.nekkolike.nekko_service.business.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class CatMapper {

    private final UserRepository userRepository;

    public Cat fromDto(CatRegistrationDto dto) {
        var user = userRepository.findByChatId(dto.getAuthorChatId())
                .orElseThrow(() -> new EntityNotFoundException("Не найден пользователь с чат-ид" + dto.getAuthorChatId()));
        return Cat.builder()
                .id(dto.getCatId())
                .user(user)
                .catName(dto.getCatName())
                .build();
    }
}
