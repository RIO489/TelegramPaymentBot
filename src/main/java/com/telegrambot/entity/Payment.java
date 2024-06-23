package com.telegrambot.entity;

import com.telegrambot.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payer_id", nullable = false)
    private Payer payer;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String paymentPurpose;

    @Column(nullable = false)
    private String recipient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "confirmation_payment_id")
    private String confirmationPaymentId;  // Ідентифікатор підтвердження платежу

}
