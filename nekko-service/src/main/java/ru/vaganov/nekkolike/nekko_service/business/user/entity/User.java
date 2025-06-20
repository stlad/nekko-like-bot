package ru.vaganov.nekkolike.nekko_service.business.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String telegramUsername;

    private String username;

    private Long chatId;
}
