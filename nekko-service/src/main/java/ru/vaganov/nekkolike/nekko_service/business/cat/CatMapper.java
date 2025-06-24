package ru.vaganov.nekkolike.nekko_service.business.cat;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.*;
import ru.vaganov.nekkolike.nekko_service.business.cat.entity.Cat;
import ru.vaganov.nekkolike.nekko_service.business.user.UserRepository;
import ru.vaganov.nekkolike.nekko_service.contentmanager.ContentManager;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CatMapper {

    private final UserRepository userRepository;
    private final ContentManager contentManager;

    public Cat fromDto(CatRegistrationDto dto) {
        var user = userRepository.findByChatId(dto.getAuthorChatId())
                .orElseThrow(() -> new EntityNotFoundException("Не найден пользователь с чат-ид" + dto.getAuthorChatId()));
        return Cat.builder()
                .id(dto.getCatId())
                .user(user)
                .catName(dto.getCatName())
                .build();
    }

    public CatListDto toListDto(Long chatId, Integer page, Integer pageSize, List<CatListElementDto> cats) {
        return CatListDto.builder()
                .authorChatId(chatId)
                .page(page)
                .pageSize(pageSize)
                .cats(cats)
                .build();
    }

    public CatReviewDto toReviewDto(Cat cat, ReviewRateDto rate) {
        var photo = contentManager.loadFile(cat.getPhotoName());
        return CatReviewDto.builder()
                .authorChatId(cat.getUser().getChatId())
                .authorTelegramUsername(cat.getUser().getTelegramUsername())
                .catName(cat.getCatName())
                .catId(cat.getId())
                .photo(photo)
                .dislikeCount(rate.getDislikeCount())
                .likeCount(rate.getLikeCount())
                .build();
    }

    public CatInfoDto toInfoDto(Cat cat) {
        var photo = contentManager.loadFile(cat.getPhotoName());
        return CatInfoDto.builder()
                .authorChatId(cat.getUser().getChatId())
                .authorTelegramUsername(cat.getUser().getTelegramUsername())
                .catName(cat.getCatName())
                .catId(cat.getId())
                .photo(photo)
                .build();
    }
}
