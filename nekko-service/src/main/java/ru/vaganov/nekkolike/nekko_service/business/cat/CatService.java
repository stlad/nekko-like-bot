package ru.vaganov.nekkolike.nekko_service.business.cat;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.common.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatInfoDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatListDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.entity.Cat;
import ru.vaganov.nekkolike.nekko_service.business.review.entity.Review;
import ru.vaganov.nekkolike.nekko_service.business.review.entity.ReviewRate;
import ru.vaganov.nekkolike.nekko_service.business.review.entity.ReviewRepository;
import ru.vaganov.nekkolike.nekko_service.business.user.UserRepository;
import ru.vaganov.nekkolike.nekko_service.contentmanager.ContentManager;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatService {
    private final CatRepository catRepository;
    private final ContentManager contentManager;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Cat createCat(CatRegistrationDto dto) {
        log.info("Регистрация котика {} от {}", dto.getCatName(), dto.getCatId());
        var user = userRepository.findByChatId(dto.getAuthorChatId())
                .orElseThrow(() -> new EntityNotFoundException("Не найден пользователь с чат-ид" + dto.getAuthorChatId()));
        var photoName = dto.getAuthorChatId() + "/" + dto.getCatId() + ".jpg";
        var cat = Cat.builder()
                .id(dto.getCatId())
                .user(user)
                .catName(dto.getCatName())
                .photoName(photoName)
                .build();

        contentManager.save(photoName, dto.getPhoto());

        return catRepository.save(cat);
    }

    @Transactional
    public CatListDto getCatNamesByAuthor(Long chatId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        var cats = catRepository.findCatNamesByChatId(chatId, pageable);
        return CatListDto.builder()
                .authorChatId(chatId)
                .page(page)
                .pageSize(pageSize)
                .cats(cats.getContent())
                .build();
    }

    @Transactional
    public CatInfoDto findRandomCat() {
        var cat = catRepository.findRandomCat();
        var photo = contentManager.loadFile(cat.getPhotoName());
        cat.setPhoto(photo);
        return cat;
    }

    @Transactional
    public void deleteCat(UUID catId) {
        catRepository.deleteById(catId);
    }

    @Transactional
    public void rateCat(UUID catId, Long rateAuthorChatId, ReviewRate rate) {
        Review review = null;
        var reviewOpt = reviewRepository.findByUserChatId(rateAuthorChatId, catId);
        review = reviewOpt.orElseGet(() ->
                Review.builder()
                        .user(userRepository.findByChatId(rateAuthorChatId).orElseThrow())
                        .cat(catRepository.findById(catId).orElseThrow())
                        .build()
        );
        review.setRate(rate);
        reviewRepository.save(review);
    }

    @Transactional
    public CatInfoDto findById(UUID catId) {
        var cat = catRepository.findById(catId).orElseThrow();
        var photo = contentManager.loadFile(cat.getPhotoName());

        return CatInfoDto.builder()
                .authorChatId(cat.getUser().getChatId())
                .authorTelegramUsername(cat.getUser().getTelegramUsername())
                .catName(cat.getCatName())
                .catId(cat.getId())
                .photo(photo)
                .photoName(cat.getPhotoName())
                .build();
    }
}
