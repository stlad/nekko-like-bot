package ru.vaganov.nekkolike.nekko_service.business.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
import ru.vaganov.nekkolike.nekko_service.business.user.dto.UserRegistrationDto;
import ru.vaganov.nekkolike.nekko_service.business.user.entity.User;
import ru.vaganov.nekkolike.nekko_service.config.BaseContextTest;
import ru.vaganov.nekkolike.nekko_service.exception.UserExistsException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends BaseContextTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    void registerUser() {
        var newUser = UserRegistrationDto.builder().telegramUsername("12321312").username("213123").chatId(1L).build();
        var user = userService.registerUser(newUser);
        Assertions.assertNotNull(user.getId());

        Assertions.assertEquals(newUser.getTelegramUsername(), user.getTelegramUsername());
        Assertions.assertEquals(newUser.getUsername(), user.getUsername());
        Assertions.assertEquals(newUser.getChatId(), user.getChatId());
    }

    @Test
    void registerUserFailsOnExisting() {
        var oldUser = User.builder().telegramUsername("old").username("old").chatId(1L).build();
        userRepository.saveAndFlush(oldUser);

        var newUser = UserRegistrationDto.builder().telegramUsername("12321312").username("213123").chatId(1L).build();

        Assertions.assertThrows(UserExistsException.class, () -> userService.registerUser(newUser));
    }
}