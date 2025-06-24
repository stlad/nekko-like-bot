package ru.vaganov.nekkolike.nekko_service.business.cat;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatInfoDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatListDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatRegistrationDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.dto.CatReviewDto;
import ru.vaganov.nekkolike.nekko_service.business.cat.entity.Cat;
import ru.vaganov.nekkolike.nekko_service.business.review.RateRepository;
import ru.vaganov.nekkolike.nekko_service.business.review.entity.Review;
import ru.vaganov.nekkolike.nekko_service.business.review.entity.ReviewRate;
import ru.vaganov.nekkolike.nekko_service.business.user.UserRepository;
import ru.vaganov.nekkolike.nekko_service.contentmanager.ContentManager;
import ru.vaganov.nekkolike.nekko_service.exception.ContentManagerException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatService {

    private final CatMapper catMapper;
    private final CatRepository catRepository;
    private final ContentManager contentManager;
    private final UserRepository userRepository;
    private final RateRepository rateRepository;

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
    public CatListDto getCatNamesByAuthor(Long chatId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        var cats = catRepository.findCatNamesByChatId(chatId, pageable);
        return catMapper.toListDto(chatId, cats.getNumber(), cats.getSize(), cats.getContent());
    }

    @Transactional
    public CatReviewDto findRandomCat() {
        var cat = catRepository.findRandomCat().orElseThrow();
        var rate = catRepository.getRates(cat.getId());
        return catMapper.toReviewDto(cat, rate);
    }

    @Transactional
    public void deleteCat(UUID catId) {
        catRepository.deleteById(catId);
    }

    @Transactional
    public void rateCat(UUID catId, Long rateAuthorChatId, ReviewRate rate) {
        var user = userRepository.findByChatId(rateAuthorChatId).orElseThrow();
        var cat = catRepository.findById(catId).orElseThrow();
        var review = Review.builder()
                .rate(rate)
                .user(user)
                .cat(cat)
                .build();
        rateRepository.save(review);
    }

    @Transactional
    public CatInfoDto findById(UUID catId) {
        var cat = catRepository.findById(catId).orElseThrow();
        return catMapper.toInfoDto(cat);
    }
}
