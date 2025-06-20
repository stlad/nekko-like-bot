package ru.vaganov.nekkolike.nekko_service.business.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.nekko_service.business.user.dto.UserRegistrationDto;
import ru.vaganov.nekkolike.nekko_service.business.user.entity.User;
import ru.vaganov.nekkolike.nekko_service.exception.UserExistsException;

import javax.validation.Valid;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public User registerUser(@Valid UserRegistrationDto dto) {
        var user = userMapper.fromDto(dto);
        try {
            user = userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            log.error("Пользователь {} уже зарегистрирован", user.getTelegramUsername());
            throw new UserExistsException(dto.getChatId(), e);
        }
        log.info("Пользователь {} зарегистрирован", user.getTelegramUsername());
        return user;
    }
}
