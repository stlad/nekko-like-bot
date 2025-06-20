package ru.vaganov.nekkolike.nekko_service.cat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.nekko_service.cat.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.nekko_service.cat.entity.Cat;
import ru.vaganov.nekkolike.nekko_service.cat.mapper.CatMapper;
import ru.vaganov.nekkolike.nekko_service.cat.repository.CatRepository;
import ru.vaganov.nekkolike.nekko_service.contentmanager.ContentManager;
import ru.vaganov.nekkolike.nekko_service.exception.ContentManagerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

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
        var photoName = dto.getAuthorChatId() + "/" + UUID.randomUUID() + ".jpg";
        cat.setPhotoName(photoName);
        try {
            contentManager.save(photoName, new FileInputStream(dto.getPhoto()));
        } catch (IOException e) {
            throw new ContentManagerException(photoName, e);
        }
        return catRepository.save(cat);
    }
}
