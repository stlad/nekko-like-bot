package ru.vaganov.nekkolike.nekko_service.config;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = TestConfig.class)
@Testcontainers
@Transactional
public class BaseContextTest {

    @Autowired
    protected EntityManager entityManager;
}
