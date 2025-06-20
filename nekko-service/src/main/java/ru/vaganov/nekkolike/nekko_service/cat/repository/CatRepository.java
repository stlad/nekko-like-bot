package ru.vaganov.nekkolike.nekko_service.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vaganov.nekkolike.nekko_service.cat.entity.Cat;

import java.util.UUID;

public interface CatRepository extends JpaRepository<Cat, UUID> {
}
