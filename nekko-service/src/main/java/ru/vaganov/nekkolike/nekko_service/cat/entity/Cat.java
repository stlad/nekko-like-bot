package ru.vaganov.nekkolike.nekko_service.cat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.vaganov.nekkolike.nekko_service.user.entity.User;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "t_cat")
public class Cat {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String photoName;

    private String catName;

    @ManyToOne
    @JoinColumn(name = "link_user", referencedColumnName = "id")
    private User user;
}
