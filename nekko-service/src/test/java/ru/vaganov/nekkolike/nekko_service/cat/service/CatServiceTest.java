package ru.vaganov.nekkolike.nekko_service.cat.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import ru.vaganov.nekkolike.nekko_service.cat.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.nekko_service.config.BaseContextTest;
import ru.vaganov.nekkolike.nekko_service.user.entity.User;
import ru.vaganov.nekkolike.nekko_service.user.repository.UserRepository;

import java.io.File;
import java.io.IOException;

class CatServiceTest extends BaseContextTest {

    private static final String RESOURCE_FILENAME = "/img/test_img.jpg";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatService catService;

    @Test
    void createCat() {
        var user = User.builder().chatId(1L).username("name").build();
        user = userRepository.saveAndFlush(user);
        var file = openFile();

        var dto = CatRegistrationDto.builder()
                .authorChatId(user.getChatId())
                .catName("cat")
                .photo(file)
                .build();

        var cat = catService.createCat(dto);
        Assertions.assertTrue(cat.getPhotoName().startsWith("1/"));
        Assertions.assertNotNull(cat.getId());
        Assertions.assertEquals(dto.getCatName(), cat.getCatName());
    }

    private File openFile() {
        try {
            return new ClassPathResource(RESOURCE_FILENAME).getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}