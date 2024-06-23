package com.telegrambot.entity;


import com.telegrambot.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

//import org.telegram.telegrambots.meta.api.objects.payments.Invoice;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") // Зміна імені таблиці на "users"
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false)
    private UserRole role;
}
