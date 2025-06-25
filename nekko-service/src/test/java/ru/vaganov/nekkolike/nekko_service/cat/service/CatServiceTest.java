package ru.vaganov.nekkolike.nekko_service.cat.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import ru.vaganov.nekkolike.nekko_service.business.cat.CatRepository;
import ru.vaganov.nekkolike.nekko_service.business.cat.CatService;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.entity.Cat;
import ru.vaganov.nekkolike.nekko_service.business.user.UserRepository;
import ru.vaganov.nekkolike.nekko_service.business.user.entity.User;
import ru.vaganov.nekkolike.nekko_service.config.BaseContextTest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

class CatServiceTest extends BaseContextTest {

    private static final String RESOURCE_FILENAME = "/img/test_img.jpg";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatService catService;
    @Autowired
    private CatRepository catRepository;

    @Test
    void createCat() {
        var user = User.builder().chatId(1L).username("name").build();
        user = userRepository.saveAndFlush(user);
        var file = openFile();

        var dto = CatRegistrationDto.builder()
                .catId(UUID.fromString("7f5333bd-ad68-4e33-95cf-6186ce001ddf"))
                .authorChatId(user.getChatId())
                .catName("cat")
                .photo(file)
                .build();

        var cat = catService.createCat(dto);

        Assertions.assertEquals(dto.getCatId(), cat.getId());
        Assertions.assertTrue(cat.getPhotoName().equals(String.format("%s/%s.jpg", user.getChatId(), dto.getCatId())));
        Assertions.assertEquals(dto.getCatName(), cat.getCatName());

    }

    @Test
    void getCatNames() {
        var user = User.builder().chatId(1L).username("name").build();
        user = userRepository.saveAndFlush(user);

        var cat1 = Cat.builder().id(UUID.randomUUID()).user(user).photoName("filename").catName("cat1").build();
        var cat2 = Cat.builder().id(UUID.randomUUID()).user(user).photoName("filename").catName("cat2").build();
        var cat3 = Cat.builder().id(UUID.randomUUID()).user(user).photoName("filename").catName("cat3").build();
        catRepository.saveAllAndFlush(List.of(cat1, cat2, cat3));

        var result = catService.getCatNamesByAuthor(user.getChatId(), 0, 1);
        Assertions.assertEquals(1, result.getCats().size());

        result = catService.getCatNamesByAuthor(user.getChatId(), 0, 2);
        Assertions.assertEquals(2, result.getCats().size());

        result = catService.getCatNamesByAuthor(user.getChatId(), 1, 2);
        Assertions.assertEquals(1, result.getCats().size());

        result = catService.getCatNamesByAuthor(user.getChatId(), 0, 3);
        Assertions.assertEquals(3, result.getCats().size());
    }

    @Test
    void findCatById() {
        var user = User.builder().chatId(1L).telegramUsername("tgname").username("username").build();
        user = userRepository.saveAndFlush(user);

        var file = openFile();

        var dto = CatRegistrationDto.builder()
                .catId(UUID.fromString("7f5333bd-ad68-4e33-95cf-6186ce001ddf"))
                .authorChatId(user.getChatId())
                .catName("cat")
                .photo(file)
                .build();
        catService.createCat(dto);
        entityManager.flush();
        entityManager.clear();

        var result = catService.findById(dto.getCatId());
        Assertions.assertEquals(dto.getCatId(), result.getCatId());
        Assertions.assertEquals(dto.getCatName(), result.getCatName());
        Assertions.assertEquals(user.getTelegramUsername(), result.getAuthorTelegramUsername());
        Assertions.assertEquals(user.getChatId(), result.getAuthorChatId());
    }

    private byte[] openFile() {
        try {
            return new ClassPathResource(RESOURCE_FILENAME).getContentAsByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}