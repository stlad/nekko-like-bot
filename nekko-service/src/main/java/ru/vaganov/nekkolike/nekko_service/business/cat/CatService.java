package ru.vaganov.nekkolike.nekko_service.business.cat;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.entity.Cat;
import ru.vaganov.nekkolike.nekko_service.contentmanager.ContentManager;
import ru.vaganov.nekkolike.nekko_service.exception.ContentManagerException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatService {

    private final CatMapper catMapper;
    private final CatRepository catRepository;
    private final ContentManager contentManager;

    @Transactional
    public Cat createCat(CatRegistrationDto dto) {
        var cat = catMapper.fromDto(dto);
        var photoName = dto.getAuthorChatId() + "/" + dto.getCatId() + ".jpg";
        cat.setPhotoName(photoName);
        try {
            contentManager.save(photoName, new ByteArrayInputStream(dto.getPhoto()));
        } catch (IOException e) {
            throw new ContentManagerException(photoName, e);
        }
        return catRepository.save(cat);
    }

    @Transactional
    public List<String> getCatNamesByAuthor(Long chatId) {
        return catRepository.findCatNamesByChatId(chatId).toList();
    }
}
