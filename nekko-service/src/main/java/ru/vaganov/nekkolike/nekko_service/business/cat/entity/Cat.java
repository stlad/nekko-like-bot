package ru.vaganov.nekkolike.nekko_service.business.cat.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.vaganov.nekkolike.nekko_service.business.user.entity.User;

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
    private UUID id;

    private String photoName;

    private String catName;

    @ManyToOne
    @JoinColumn(name = "link_user", referencedColumnName = "id")
    private User user;
}
