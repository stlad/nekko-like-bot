package ru.vaganov.nekkolike.nekko_service.business.review;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vaganov.nekkolike.nekko_service.business.review.entity.Review;

import java.util.UUID;

public interface RateRepository extends JpaRepository<Review, UUID> {
}
