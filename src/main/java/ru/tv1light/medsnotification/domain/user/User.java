package ru.tv1light.medsnotification.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users",
    uniqueConstraints = @UniqueConstraint(columnNames = "login"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Логин: 4..12 символов */
    @Column(nullable = false, length = 12, unique = true)
    @Size(min = 4, max = 12)
    private String login;

    /** Пароль: 8..14 символов (plain-text для MVP) */
    @Column(nullable = false, length = 14)
    @Size(min = 8, max = 14)
    private String password;
}
