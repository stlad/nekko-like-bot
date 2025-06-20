package ru.vaganov.nekkolike.nekko_service.review.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.vaganov.nekkolike.nekko_service.cat.entity.Cat;
import ru.vaganov.nekkolike.nekko_service.user.entity.User;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "t_review")
public class Review {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ReviewRate rate;


    @ManyToOne
    @JoinColumn(name = "link_user", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "link_cat", referencedColumnName = "id")
    private Cat cat;


}
