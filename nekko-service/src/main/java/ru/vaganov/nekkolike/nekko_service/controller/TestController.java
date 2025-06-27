package ru.vaganov.nekkolike.nekko_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.vaganov.nekkolike.common.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.CatService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final CatService catService;

    @Operation(description = "Загруить котика в систему")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadCat(@RequestParam Long chatId,
                          @RequestParam("photo") MultipartFile photo) {
        try {
            var dto = CatRegistrationDto.builder()
                    .catId(UUID.randomUUID())
                    .authorChatId(chatId)
                    .photo(photo.getBytes())
                    .catName(photo.getOriginalFilename().split("\\.")[0])
                    .build();
            catService.createCat(dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
