package ru.vaganov.nekkolike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;

@SpringBootApplication
@EnableScheduling
@Import(TelegramBotStarterConfiguration.class)
public class NekkolikeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NekkolikeApplication.class, args);
    }

}
